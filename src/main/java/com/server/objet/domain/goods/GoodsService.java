package com.server.objet.domain.goods;

import com.server.objet.domain.goods.dto.*;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.dto.ContentData.ImageContent;
import com.server.objet.global.dto.ContentData.SpaceContent;
import com.server.objet.global.dto.ContentData.TextContent;
import com.server.objet.global.entity.*;
import com.server.objet.global.enums.GoodsType;
import com.server.objet.global.exception.UserNotFoundException;
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
    private final CartRepository cartRepository;
    private final GoodsThumbNailRepository goodsThumbNailRepository;

    public MainPageGoods getPopularGoods(GoodsType goodsType) {
        List<MainPageGoodsInfo> mainPageGoodsInfos = new ArrayList<>();
        List<Goods> popularGoods = goodsRepository.findTop12ByTypeOrderByUploadAtDesc(goodsType.toString());

        Integer cnt = 1;
        for(Goods goods: popularGoods) {
            Artist artist = artistRepository.findById(goods.getArtistId()).get();
            GoodsDetail goodsDetail = goodsDetailRepository
                    .findTop1ByGoodsIdAndTypeOrderByContentOrderAsc(goods.getId(), "image").get();

            GoodsThumbNail goodsThumbNail = goodsThumbNailRepository
                    .findByGoodsIdAndAndContentOrder(goods.getId(), 1L).get();

            MainPageGoodsInfo mainPageProductInfo = MainPageGoodsInfo.builder()
                    .rank(cnt)
                    .goodsId(goods.getId())
                    .title(goods.getName())
                    .category(goods.getType())
                    .price(goods.getPrice())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(goodsThumbNail.getUrl())
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

        List<Object> resultGoodsDetails = makeGoodsDetailsList(goods.getDetails());
        List<ThumbNailInfo> goodsThumbNailInfos = makeGoodsThumbNailList(goods.getThumbNails());

        GoodsDetailInfo goodsDetailInfo = GoodsDetailInfo.builder()
                .goodsId(id)
                .name(goods.getName())
                .category(goods.getType())
                .description(goods.getDescription())
                .isInclude(goods.getIsInclude())
                .deliveryCharge(goods.getDeliveryCharge())
                .price(goods.getPrice())
                .artistName(artist.getUser().getName())
                .artistInfo(artist.getComment())
                .artistPicPath(artist.getProfilePicUrl())
                .thumbnails(goodsThumbNailInfos)
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
                .isInclude(goodsInfo.getIsInclude())
                .deliveryCharge(goodsInfo.getDeliveryCharge())
                .price(goodsInfo.getPrice())
                .uploadAt(LocalDateTime.now())
                .build();
        goodsRepository.save(goods);

        Long goodsId = goods.getId();

        for (ThumbNailInfo thumbNailInfo : goodsInfo.getThumbnails()) {
            GoodsThumbNail goodsThumbNail = GoodsThumbNail.builder()
                    .goodsId(goodsId)
                    .contentOrder(thumbNailInfo.getContentOrder())
                    .url(thumbNailInfo.getUrl())
                    .build();
            goodsThumbNailRepository.save(goodsThumbNail);
        }

        for (Object artistContent : goodsInfo.getGoodsDetails()) {
            String goodsDetail = artistContent.toString();
            parseContent(goodsDetail, goodsId);
        }

        return new RegisterNormalGoodsResult("Success", goodsId);
    }

    public String addGoodsToCart(CustomUserDetails userDetails, CartRegisterInfo cartRegisterInfo) {
        User user = userDetails.getUser();
        Goods goods = goodsRepository.findById(cartRegisterInfo.getGoodsId())
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 굿즈입니다."));

        Cart cart = Cart.builder()
                .userId(user.getId())
                .goods(goods)
                .cnt(cartRegisterInfo.getCount())
                .createAt(LocalDateTime.now())
                .build();

        cartRepository.save(cart);

        return "장바구니 추가 성공";
    }

    public CartItems getCartItems(CustomUserDetails userDetails) {
        User user = userDetails.getUser();

        List<CartItemInfo> cartItemInfos = new ArrayList<>();
        Long totalPrice = 0L;

        List<Cart> carts = cartRepository.findByUserId(user.getId());

        for(Cart cart : carts) {
            Goods goods = cart.getGoods();
            Artist artist = artistRepository.findById(goods.getArtistId())
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 아티스트입니다."));

            CartItemInfo cartItemInfo = CartItemInfo.builder()
                    .cartId(cart.getId())
                    .artistId(artist.getId())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .goodsId(goods.getId())
                    .goodsName(goods.getName())
                    .isInclude(goods.getIsInclude())
                    .deliveryCharge(goods.getDeliveryCharge())
                    .price(goods.getPrice())
                    .cnt(cart.getCnt())
                    .createAt(cart.getCreateAt())
                    .build();

            totalPrice += cartItemInfo.getPrice() * cartItemInfo.getCnt() + cartItemInfo.getDeliveryCharge();

            cartItemInfos.add(cartItemInfo);
        }

        return new CartItems(totalPrice, cartItemInfos);
    }

    public String deleteIncartGoods(CustomUserDetails userDetails, Long cartId) {
        Long userId = userDetails.getUser().getId();

        Cart cart = cartRepository.findByUserIdAndId(userId, cartId)
                .orElseThrow(() -> new UserNotFoundException("올바르지 않은 접근입니다."));

        cartRepository.delete(cart);

        return "장바구니 삭제 성공";
    }

    public String recountGoods(CustomUserDetails userDetails, CartGoodsRecount cartGoodsRecount) {
        User user = userDetails.getUser();
        Cart cart = cartRepository.findByUserIdAndId(user.getId(), cartGoodsRecount.getCartId())
                .orElseThrow(() -> new UserNotFoundException("올바르지 않은 접근입니다."));

        cart.updateCount(cartGoodsRecount.getNewCount());
        cartRepository.save(cart);

        return "장바구니 수량 변경 성공";
    }

    @Transactional
    private List<ThumbNailInfo> makeGoodsThumbNailList(List<GoodsThumbNail> goodsThumbNails) {
        List<ThumbNailInfo> thumbNailInfos = new ArrayList<>();

        for (GoodsThumbNail goodsThumbNail : goodsThumbNails) {
            ThumbNailInfo thumbNailInfo =
                    new ThumbNailInfo(goodsThumbNail.getContentOrder(), goodsThumbNail.getUrl());
            thumbNailInfos.add(thumbNailInfo);
        }

        return thumbNailInfos;
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
