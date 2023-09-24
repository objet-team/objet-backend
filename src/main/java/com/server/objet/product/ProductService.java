package com.server.objet.product;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.Image;
import com.server.objet.global.entity.Like;
import com.server.objet.global.entity.Product;
import com.server.objet.global.repository.ImageRepository;
import com.server.objet.global.repository.LikeRepository;
import com.server.objet.global.repository.ProductRepository;
import com.server.objet.global.repository.ArtistRepository;

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
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public PopularProducts getPopularProducts() {
        // ToDo: optional 처리하기

        List<PopularProductInfo> popularProductInfos = new ArrayList<>();
        List<Like> productIds = likeRepository.findTop20ByOrderByCountDesc();

        Integer cnt = 1;
        for(Like like: productIds) {
            Product product = like.getProduct();
            Artist artist = artistRepository.findById(product.getArtistId()).get();
            Image image = imageRepository.findByProductIdAndOrder(product.getId(),1).get();

            PopularProductInfo popularProductInfo = PopularProductInfo.builder()
                    .rank(cnt)
                    .productId(product.getId())
                    .title(product.getTitle())
                    .like(like.getCount())
                    .artistName(artist.getUser().getName())
                    .artistPicPath(artist.getProfilePicUrl())
                    .thumbNailPath(image.getImgPath())
                    .build();


            popularProductInfos.add(popularProductInfo);
            cnt++;

        }

        PopularProducts popularProducts = new PopularProducts(popularProductInfos);

        return popularProducts;
    }

    public ProductDetail getProductDetail(Long id) {
        // ToDo: optional 처리하기

        Product product = productRepository.findById(id).get();
        Like like = likeRepository.findByProduct(product).get();
        Artist artist = artistRepository.findById(product.getArtistId()).get();
        List<Image> images = product.getImages();

        ProductDetail productDetail = ProductDetail.builder()
                .productId(id)
                .title(product.getTitle())
                .detail(product.getDesc())
                .like(like.getCount())
                .artistName(artist.getUser().getName())
                .artistInfo(artist.getComment())
                .artistPicPath(artist.getProfilePicUrl())
                .images(images)
                .build();

        return productDetail;
    }

}
