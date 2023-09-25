package com.server.objet.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SpaceContent {
    private Long productId;
    private String type;
    private Integer order;

}