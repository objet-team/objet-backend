package com.server.objet.domain.product.dto.Content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TextContent {
    private Long productId;
    private String type;
    private Integer order;
    private String sizeType;
    private String description;
    private String align;

}
