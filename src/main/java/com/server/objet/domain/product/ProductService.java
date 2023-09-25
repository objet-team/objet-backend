package com.server.objet.domain.product;

import com.server.objet.domain.product.dto.*;
import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.Content;
import com.server.objet.global.entity.Like;
import com.server.objet.global.entity.Product;
import com.server.objet.global.repository.*;

import com.server.objet.domain.product.dto.*;
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

    @Transactional
    private List<Object> makeContentsList(List<Content> contents) {
        List<Object> resultContents = new ArrayList<>();

        for(Content content : contents) {
            String type = content.getType();
            if (type.equals("image")) {
                ImageContent imageContent = ImageContent.builder()
                        .productId(content.getProductId())
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
                        .productId(content.getProductId())
                        .type(content.getType())
                        .order(content.getContentOrder())
                        .sizeType(content.getSizeType())
                        .description(content.getDescription())
                        .align(content.getAlign())
                        .build();
                resultContents.add(textContent);
            } else if (type.equals("space")) {
                SpaceContent spaceContent = SpaceContent.builder()
                        .productId(content.getProductId())
                        .type(content.getType())
                        .order(content.getContentOrder())
                        .build();
                resultContents.add(spaceContent);
            }

        }
        return resultContents;
    }

}
