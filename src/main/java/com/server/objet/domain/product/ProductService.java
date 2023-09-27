package com.server.objet.domain.product;

import com.server.objet.domain.auth.CustomUserDetails;
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
        // ToDo: optional 처리하기

        List<MainPageProductInfo> mainPageProductInfos = new ArrayList<>();
        List<Like> productIds = likeRepository.findTop20ByOrderByCountDesc();

        Integer cnt = 1;
        for(Like like: productIds) {
            Product product = like.getProduct();
            Artist artist = artistRepository.findById(product.getArtistId()).get();
            Content content = contentRepository
                    .findTop1ByProductIdAndTypeOrderByContentOrderAsc(product.getId(), "image").get();

            MainPageProductInfo mainPageProductInfo = MainPageProductInfo.builder()
                    .rank(cnt)
                    .productId(product.getId())
                    .title(product.getTitle())
                    .category(product.getCategory())
                    .like(like.getCount())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(content.getUrl())
                    .build();


            mainPageProductInfos.add(mainPageProductInfo);
            cnt++;

        }

        MainPageProducts mainPageProducts = new MainPageProducts(mainPageProductInfos);

        return mainPageProducts;
    }

    public MainPageProducts getNewProducts() {
        // ToDo: optional 처리하기

        List<MainPageProductInfo> mainPageProductInfos = new ArrayList<>();
        List<Product> products = productRepository.findTop12ByOrderByUploadAtDesc();

        Integer cnt = 1;
        for(Product product: products) {
            Like like = likeRepository.findByProduct(product).get();
            Artist artist = artistRepository.findById(product.getArtistId()).get();
            Content content = contentRepository
                    .findTop1ByProductIdAndTypeOrderByContentOrderAsc(product.getId(), "image").get();

            MainPageProductInfo mainPageProductInfo = MainPageProductInfo.builder()
                    .rank(cnt)
                    .productId(product.getId())
                    .title(product.getTitle())
                    .category(product.getCategory())
                    .like(like.getCount())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(content.getUrl())
                    .build();


            mainPageProductInfos.add(mainPageProductInfo);
            cnt++;

        }

        MainPageProducts mainPageProducts = new MainPageProducts(mainPageProductInfos);

        return mainPageProducts;

    }

    @Transactional
    public ProductDetail getProductDetail(Long id) {
        // ToDo: optional 처리하기

        Product product = productRepository.findById(id).get();
        Like like = likeRepository.findByProduct(product).get();
        Artist artist = artistRepository.findById(product.getArtistId()).get();
        List<Content> contents = product.getContents();

        List<Object> resultContents = makeContentsList(contents);

        ProductDetail productDetail = ProductDetail.builder()
                .productId(id)
                .title(product.getTitle())
                .category(product.getCategory())
                .detail(product.getDesc())
                .like(like.getCount())
                .artistName(artist.getUser().getName())
                .artistInfo(artist.getComment())
                .artistPicPath(artist.getProfilePicUrl())
                .contents(resultContents)
                .build();

        return productDetail;
    }

    public RegisterProductResult doRegisterProduct(ProductInfo productInfo, CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        Long artistId = artistRepository.findByUser(user).get().getId();

        Product product = Product.builder()
                .artistId(artistId)
                .title(productInfo.getTitle())
                .desc(productInfo.getDescription())
                .category(productInfo.getCategory())
                .uploadAt(LocalDateTime.now())
                .build();
        productRepository.save(product);

        Long productId = product.getId();

        for (Object artistContent : productInfo.getContents()) {
            String content = artistContent.toString();
            parseContent(content, productId);
        }

        Long defaultCount = (Long) 0L;

        Like like = Like.builder()
                .product(productRepository.findById(productId).get())
                .count(defaultCount)
                .build();

        likeRepository.save(like);

        return new RegisterProductResult("Success", productId);
    }

    @Transactional
    private List<Object> makeContentsList(List<Content> contents) {
        List<Object> resultContents = new ArrayList<>();

        for(Content content : contents) {
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

        HashMap <String, String> contentMap = new HashMap<>();
        for (String name: parsing) {
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

}
