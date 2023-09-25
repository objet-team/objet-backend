package com.server.objet.product.dto;

import com.server.objet.global.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class ProductDetail {
    private Long productId;
    private String title;
    private String category;
    private String detail;
    private Long like;
    private String artistName;
    private String artistInfo;
    private String artistPicPath;
    private List<Content> contents;
}
