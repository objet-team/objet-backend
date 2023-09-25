package com.server.objet.domain.product.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ImageContent {
    private Long productId;
    private String type;
    private Integer order;
    private String url;
    private String align;
    private Long width;
    private Long height;
}
