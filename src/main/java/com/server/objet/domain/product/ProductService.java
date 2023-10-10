package com.server.objet.domain.product;

import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.product.dto.res.ActionResult;
import com.server.objet.global.dto.ContentData.ImageContent;
import com.server.objet.global.dto.ContentData.SpaceContent;
import com.server.objet.global.dto.ContentData.TextContent;
import com.server.objet.domain.product.dto.req.ProductInfo;
import com.server.objet.domain.product.dto.res.MainPageProductInfo;
import com.server.objet.domain.product.dto.res.MainPageProducts;
import com.server.objet.domain.product.dto.ProductDetail;
import com.server.objet.domain.product.dto.res.RegisterProductResult;
import com.server.objet.global.entity.*;
import com.server.objet.global.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final LikeRepository likeRepository;
    private final ArtistRepository artistRepository;
    private final ContentRepository contentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public MainPageProducts getPopularProducts() {
        List<Product> products = productRepository.findTop12ByOrderByLikeCount();
        List<MainPageProductInfo> mainPageProductInfos = makeMainPageInfoByProduct(products);

        return new MainPageProducts(mainPageProductInfos);
    }

    public MainPageProducts getWeeklyPopularProducts() {
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = productRepository.findTop8ByUploadAtBetweenOrderByLikeCount(now.minusDays(7), now);
        List<MainPageProductInfo> mainPageProductInfos = makeMainPageInfoByProduct(products);

        return new MainPageProducts(mainPageProductInfos);
    }

    public MainPageProducts getNewProducts() {
        List<Product> products = productRepository.findTop12ByOrderByUploadAtDesc();
        List<MainPageProductInfo> mainPageProductInfos = makeMainPageInfoByProduct(products);

        return new MainPageProducts(mainPageProductInfos);

    }

    @Transactional
    public ProductDetail getProductDetail(Long id) {
        // ToDo: optional 처리하기

        Product product = productRepository.findById(id).get();
        Artist artist = artistRepository.findById(product.getArtistId()).get();
        List<Content> contents = product.getContents();

        List<Object> resultContents = makeContentsList(contents);

        ProductDetail productDetail = ProductDetail.builder()
                .artistId(artist.getId())
                .productId(id)
                .title(product.getTitle())
                .category(product.getCategory())
                .detail(product.getDesc())
                .like(product.getLikeCount())
                .artistName(artist.getUser().getName())
                .artistInfo(artist.getComment())
                .artistPicPath(artist.getUser().getProfilePicUrl())
                .contents(resultContents)
                .build();

        return productDetail;
    }

    public RegisterProductResult doRegisterProduct(ProductInfo productInfo, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long artistId = artistRepository.findByUser(user).get().getId();

        Long productId = makeProduct(productInfo, artistId);

        for (Object artistContent : productInfo.getContents()) {
            String content = artistContent.toString();
            parseContent(content, productId);
        }

        return new RegisterProductResult("Success", productId);
    }

    @Transactional
    public ActionResult addLike(CustomUserDetails userDetails, Long productId) {
        Long userId = userDetails.getUser().getId();
        Like like = Like.builder()
                .productId(productId)
                .userId(userId)
                .build();
        likeRepository.save(like);

        updateCurrentLikeCount(userId, productId);

        return new ActionResult("좋아요 추가", "성공");
    }

    @Transactional
    public ActionResult deleteLike(CustomUserDetails userDetails, Long productId) {
        Long userId = userDetails.getUser().getId();
        Like like = likeRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 작품에 추가한 좋아요가 없습니다."));

        likeRepository.delete(like);

        updateCurrentLikeCount(userId, productId);

        return new ActionResult("좋아요 취소", "성공");
    }

    @Transactional
    public ActionResult deleteProduct(CustomUserDetails userDetails, Long productId) {
        User user = userDetails.getUser();
        Artist artist = artistRepository.findByUser(user).get();
        Product product = productRepository.findByArtistIdAndId(artist.getId(), productId)
                .orElseThrow(() -> new UsernameNotFoundException("올바르지 않은 접근입니다."));

        likeRepository.deleteByProductId(productId);
        contentRepository.deleteByProductId(productId);
        productRepository.delete(product);

        return new ActionResult("작품 삭제", "성공");
    }

    @Transactional
    private void updateCurrentLikeCount(Long userId, Long productId) {
        Long likeCount = likeRepository.countByUserIdAndProductId(userId, productId);
        System.out.println(likeCount);
        Product product = productRepository.findById(productId).get();
        product.updateLikeCount(likeCount);
    }

    private List<MainPageProductInfo> makeMainPageInfoByProduct(List<Product> products) {
        List<MainPageProductInfo> mainPageProductInfos = new ArrayList<>();
        Integer cnt = 1;
        for (Product product : products) {
            Artist artist = artistRepository.findById(product.getArtistId()).get();
            Content content = contentRepository
                    .findTop1ByProductIdAndTypeOrderByContentOrderAsc(product.getId(), "image").get();
            User user = userRepository.findById(artist.getUser().getId()).get();

            MainPageProductInfo mainPageProductInfo = MainPageProductInfo.builder()
                    .rank(cnt)
                    .productId(product.getId())
                    .title(product.getTitle())
                    .category(product.getCategory())
                    .like(product.getLikeCount())
                    .artistId(artist.getId())
                    .artistName(user.getName())
                    .artistPicPath(user.getProfilePicUrl())
                    .thumbNailPath(content.getUrl())
                    .build();

            mainPageProductInfos.add(mainPageProductInfo);
            cnt++;

        }
        return mainPageProductInfos;
    }

    @Transactional
    private List<Object> makeContentsList(List<Content> contents) {
        List<Object> resultContents = new ArrayList<>();

        for (Content content : contents) {
            String type = content.getType();
            if (type.equals("image")) {
                ImageContent imageContent = ImageContent.builder()
                        .id(content.getProductId())
                        .type(content.getType())
                        .order(content.getContentOrder())
                        .url(content.getUrl())
                        .align(content.getAlign())
                        .width(content.getWidth())
                        .height(content.getHeight())
                        .build();
                resultContents.add(imageContent);
            } else if (type.equals("text")) {
                TextContent textContent = TextContent.builder()
                        .id(content.getProductId())
                        .type(content.getType())
                        .order(content.getContentOrder())
                        .sizeType(content.getSizeType())
                        .description(content.getDescription())
                        .align(content.getAlign())
                        .build();
                resultContents.add(textContent);
            } else if (type.equals("space")) {
                SpaceContent spaceContent = SpaceContent.builder()
                        .id(content.getProductId())
                        .type(content.getType())
                        .order(content.getContentOrder())
                        .build();
                resultContents.add(spaceContent);
            }

        }
        return resultContents;
    }

    private void parseContent(String rawContent, Long productId) {
        String str = rawContent.substring(1, rawContent.length() - 1);
        List<String> parsing = Arrays.asList(str.split(","));

        HashMap<String, String> contentMap = new HashMap<>();
        for (String name : parsing) {
            String[] tokens = name.trim().split("=", 2);
            contentMap.put(tokens[0], tokens[1]);
        }

        if (contentMap.get("type").equals("text")) {
            Content content = Content.builder()
                    .type(contentMap.get("type"))
                    .productId(productId)
                    .contentOrder(Integer.parseInt(contentMap.get("order")))
                    .sizeType(contentMap.get("sizeType"))
                    .description(contentMap.get("description"))
                    .align(contentMap.get("align"))
                    .url(null)
                    .width(null)
                    .height(null)
                    .build();
            contentRepository.save(content);
        } else if (contentMap.get("type").equals("image")) {
            Content content = Content.builder()
                    .type(contentMap.get("type"))
                    .productId(productId)
                    .contentOrder(Integer.parseInt(contentMap.get("order")))
                    .sizeType(null)
                    .description(null)
                    .align(contentMap.get("align"))
                    .url(contentMap.get("url"))
                    .width(Long.parseLong(contentMap.get("width")))
                    .height(Long.parseLong(contentMap.get("height")))
                    .build();
            contentRepository.save(content);
        } else if (contentMap.get("type").equals("space")) {
            Content content = Content.builder()
                    .type(contentMap.get("type"))
                    .productId(productId)
                    .contentOrder(Integer.parseInt(contentMap.get("order")))
                    .sizeType(null)
                    .description(null)
                    .align(null)
                    .url(null)
                    .width(null)
                    .height(null)
                    .build();
            contentRepository.save(content);

        }
    }

    private Long makeProduct(ProductInfo productInfo, Long artistId) {
        Long defaultCount = (Long) 0L;

        Product product = Product.builder()
                .artistId(artistId)
                .title(productInfo.getTitle())
                .desc(productInfo.getDescription())
                .category(productInfo.getCategory())
                .likeCount(defaultCount)
                .uploadAt(LocalDateTime.now())
                .build();
        productRepository.save(product);

        return product.getId();
    }

}
