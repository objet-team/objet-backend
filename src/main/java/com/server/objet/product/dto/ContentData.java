package com.server.objet.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ContentData {
    protected Long productId;
    protected String type;
    protected Long order;

}
