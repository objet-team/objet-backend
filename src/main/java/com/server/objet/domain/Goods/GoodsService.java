package com.server.objet.domain.Goods;

import com.server.objet.domain.Goods.dto.*;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.product.dto.res.RegisterProductResult;
import com.server.objet.global.dto.ContentData.ImageContent;
import com.server.objet.global.dto.ContentData.SpaceContent;
import com.server.objet.global.dto.ContentData.TextContent;
import com.server.objet.domain.product.dto.req.ProductInfo;
import com.server.objet.global.entity.*;
import com.server.objet.global.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {


    private final GoodsRepository goodsRepository;
    private final ArtistRepository artistRepository;
    private final GoodsDetailRepository goodsDetailRepository;
    private final UserRepository userRepository;

    public MainPageGoods getPopularGoods(String type) {
        List<MainPageGoodsInfo> mainPageGoodsInfos = new ArrayList<>();
        List<Goods> popularGoods = goodsRepository.findTop12ByTypeOrderByUploadAtDesc(type);

        Integer cnt = 1;
        for(Goods goods: popularGoods) {
            Artist artist = artistRepository.findById(goods.getArtistId()).get();
            GoodsDetail goodsDetail = goodsDetailRepository
                    .findTop1ByGoodsIdAndTypeOrderByContentOrderAsc(goods.getId(), "image").get();

            MainPageGoodsInfo mainPageProductInfo = MainPageGoodsInfo.builder()
                    .rank(cnt)
                    .goodsId(goods.getId())
                    .title(goods.getName())
                    .category(goods.getType())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(goodsDetail.getUrl())
                    .build();


            mainPageGoodsInfos.add(mainPageProductInfo);
            cnt++;

        }

        MainPageGoods mainPageGoods = new MainPageGoods(mainPageGoodsInfos);

        return mainPageGoods;
    }


    @Transactional
    public GoodsDetailInfo getGoodsDetail(Long id) {
        Goods goods = goodsRepository.findById(id).get();
        Artist artist = artistRepository.findById(goods.getArtistId()).get();
        List<GoodsDetail> goodsDetails = goods.getDetails();

        List<Object> resultGoodsDetails = makeGoodsDetailsList(goodsDetails);

        GoodsDetailInfo goodsDetailInfo = GoodsDetailInfo.builder()
                .goodsId(id)
                .name(goods.getName())
                .category(goods.getType())
                .description(goods.getDescription())
                .artistName(artist.getUser().getName())
                .artistInfo(artist.getComment())
                .artistPicPath(artist.getProfilePicUrl())
                .contents(resultGoodsDetails)
                .build();

        return goodsDetailInfo;
    }

    public RegisterNormalGoodsResult doRegisterNormalGoods(GoodsInfo goodsInfo, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long artistId = artistRepository.findByUser(user).get().getId();

        Goods goods = Goods.builder()
                .artistId(artistId)
                .name(goodsInfo.getTitle())
                .description(goodsInfo.getDescription())
                .type(goodsInfo.getCategory())
                .uploadAt(LocalDateTime.now())
                .build();
        goodsRepository.save(goods);

        Long goodsId = goods.getId();

        for (Object artistContent : goodsInfo.getGoodsDetails()) {
            String goodsDetail = artistContent.toString();
            parseContent(goodsDetail, goodsId);
        }

        return new RegisterNormalGoodsResult("Success", goodsId);
    }


    @Transactional
    private List<Object> makeGoodsDetailsList(List<GoodsDetail> goodsDetails) {
        List<Object> resultGoodsDetails = new ArrayList<>();

        for(GoodsDetail goodsDetail: goodsDetails) {
            String type = goodsDetail.getType();
            if (type.equals("image")) {
                ImageContent imageContent = ImageContent.builder()
                        .id(goodsDetail.getGoodsId())
                        .type(goodsDetail.getType())
                        .order(goodsDetail.getContentOrder())
                        .url(goodsDetail.getUrl())
                        .align(goodsDetail.getAlign())
                        .width(goodsDetail.getWidth())
                        .height(goodsDetail.getHeight())
                        .build();
                resultGoodsDetails.add(imageContent);
            } else if (type.equals("text")) {
                TextContent textContent = TextContent.builder()
                        .id(goodsDetail.getGoodsId())
                        .type(goodsDetail.getType())
                        .order(goodsDetail.getContentOrder())
                        .sizeType(goodsDetail.getSizeType())
                        .description(goodsDetail.getDescription())
                        .align(goodsDetail.getAlign())
                        .build();
                resultGoodsDetails.add(textContent);
            } else if (type.equals("space")) {
                SpaceContent spaceContent = SpaceContent.builder()
                        .id(goodsDetail.getGoodsId())
                        .type(goodsDetail.getType())
                        .order(goodsDetail.getContentOrder())
                        .build();
                resultGoodsDetails.add(spaceContent);
            }

        }
        return resultGoodsDetails;
    }

    private void parseContent(String rawContent, Long goodsId) {
        String str = rawContent.substring(1, rawContent.length() - 1);
        List<String> parsing = Arrays.asList(str.split(","));

        HashMap<String, String> contentMap = new HashMap<>();
        for (String name: parsing) {
            String[] tokens = name.trim().split("=", 2);
            contentMap.put(tokens[0], tokens[1]);
        }

        if (contentMap.get("type").equals("text")) {
            GoodsDetail goodsDetail = GoodsDetail.builder()
                    .type(contentMap.get("type"))
                    .goodsId(goodsId)
                    .contentOrder(Integer.parseInt(contentMap.get("order")))
                    .sizeType(contentMap.get("sizeType"))
                    .description(contentMap.get("description"))
                    .align(contentMap.get("align"))
                    .url(null)
                    .width(null)
                    .height(null)
                    .build();
            goodsDetailRepository.save(goodsDetail);
        } else if (contentMap.get("type").equals("image")) {
            GoodsDetail goodsDetail = GoodsDetail.builder()
                    .type(contentMap.get("type"))
                    .goodsId(goodsId)
                    .contentOrder(Integer.parseInt(contentMap.get("order")))
                    .sizeType(null)
                    .description(null)
                    .align(contentMap.get("align"))
                    .url(contentMap.get("url"))
                    .width(Long.parseLong(contentMap.get("width")))
                    .height(Long.parseLong(contentMap.get("height")))
                    .build();
            goodsDetailRepository.save(goodsDetail);
        } else if (contentMap.get("type").equals("space")) {
            GoodsDetail goodsDetail = GoodsDetail.builder()
                    .type(contentMap.get("type"))
                    .goodsId(goodsId)
                    .contentOrder(Integer.parseInt(contentMap.get("order")))
                    .sizeType(null)
                    .description(null)
                    .align(null)
                    .url(null)
                    .width(null)
                    .height(null)
                    .build();
            goodsDetailRepository.save(goodsDetail);

        }
    }
}
