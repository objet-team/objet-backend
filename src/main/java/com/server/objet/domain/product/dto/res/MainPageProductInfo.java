package com.server.objet.domain.product.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MainPageProductInfo {
    private Integer rank;
    private Long productId;
    private String title;
    private String category;
    private Long like;
    private String artistName;
    private String artistPicPath;
    private String thumbNailPath;
}
