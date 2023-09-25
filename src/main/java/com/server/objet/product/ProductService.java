package com.server.objet.product;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.Content;
import com.server.objet.global.entity.Like;
import com.server.objet.global.entity.Product;
import com.server.objet.global.repository.*;

import com.server.objet.product.dto.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<Content> resultContents = makeContentsList(contents);

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

    @Transactional
    private List<Content> makeContentsList(List<Content> contents) {
        List<Content> resultContents = new ArrayList<>();
        Content currentContent = new Content();

        for(Content content : contents) {
            String type = content.getType();
            if (type.equals("image")) {
                currentContent = Content.builder()
                        .id(content.getProductId())
                        .type(content.getType())
                        .contentOrder(content.getContentOrder())
                        .url(content.getUrl())
                        .align(content.getAlign())
                        .width(content.getWidth())
                        .height(content.getHeight())
                        .build();
            } else if (type.equals("text")) {
                currentContent = Content.builder()
                        .id(content.getProductId())
                        .type(content.getType())
                        .contentOrder(content.getContentOrder())
                        .sizeType(content.getSizeType())
                        .description(content.getDescription())
                        .align(content.getAlign())
                        .build();
            } else if (type.equals("space")) {
                currentContent = Content.builder()
                        .id(content.getProductId())
                        .type(content.getType())
                        .contentOrder(content.getContentOrder())
                        .build();
            }

            resultContents.add(currentContent);
        }
        return resultContents;
    }

}
