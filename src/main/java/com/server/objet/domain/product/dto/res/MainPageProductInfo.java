package com.server.objet.domain.product.dto.res;

import com.server.objet.global.enums.Category;
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
    private Category category;
    private Long like;
    private Long artistId;
    private String artistName;
    private String artistPicPath;
    private String thumbNailPath;
}
