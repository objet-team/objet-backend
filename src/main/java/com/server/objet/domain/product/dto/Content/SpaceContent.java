package com.server.objet.domain.product.dto.Content;

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
