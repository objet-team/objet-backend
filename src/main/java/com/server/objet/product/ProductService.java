package com.server.objet.product;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.Content;
import com.server.objet.global.entity.Like;
import com.server.objet.global.entity.Product;
import com.server.objet.global.repository.*;

import com.server.objet.product.dto.*;
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
            Content content = contentRepository.findByProductIdAndOrder(product.getId(),1).get();

            MainPageProductInfo mainPageProductInfo = MainPageProductInfo.builder()
                    .rank(cnt)
                    .productId(product.getId())
                    .title(product.getTitle())
                    .category(product.getCategory())
                    .like(like.getCount())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(content.getImgPath())
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
            Content content = contentRepository.findByProductIdAndOrder(product.getId(),1).get();

            MainPageProductInfo mainPageProductInfo = MainPageProductInfo.builder()
                    .rank(cnt)
                    .productId(product.getId())
                    .title(product.getTitle())
                    .category(product.getCategory())
                    .like(like.getCount())
                    .artistName(userRepository.findById(artist.getUser().getId()).get().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(content.getImgPath())
                    .build();


            mainPageProductInfos.add(mainPageProductInfo);
            cnt++;

        }

        MainPageProducts mainPageProducts = new MainPageProducts(mainPageProductInfos);

        return mainPageProducts;

    }

    public ProductDetail getProductDetail(Long id) {
        // ToDo: optional 처리하기

        Product product = productRepository.findById(id).get();
        Like like = likeRepository.findByProduct(product).get();
        Artist artist = artistRepository.findById(product.getArtistId()).get();
        List<Content> contents = product.getContents();

        ProductDetail productDetail = ProductDetail.builder()
                .productId(id)
                .title(product.getTitle())
                .category(product.getCategory())
                .detail(product.getDesc())
                .like(like.getCount())
                .artistName(artist.getUser().getName())
                .artistInfo(artist.getComment())
                .artistPicPath(artist.getProfilePicUrl())
                .contents(contents)
                .build();

        return productDetail;
    }

}
