package com.server.objet.domain.product.dto;

import com.server.objet.global.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ProductDetail {
    private Long productId;
    private Long artistId;
    private String title;
    private Category category;
    private String detail;
    private Long like;
    private String artistName;
    private String artistInfo;
    private String artistPicPath;
    private List<Object> contents;
}
