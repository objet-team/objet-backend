package com.server.objet.product.dto;

import com.server.objet.global.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class ProductDetail {
    private Long productId;
    private String title;
    private String detail;
    private Long like;
    private String artistName;
    private String artistInfo;
    private String artistPicPath;
    private List<Image> images;
}
