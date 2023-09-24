package com.server.objet.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PopularProductInfo {
    private Integer rank;
    private Long productId;
    private String title;
    private Long like;
    private String artistName;
    private String artistPicPath;
    private String thumbNailPath;
}
