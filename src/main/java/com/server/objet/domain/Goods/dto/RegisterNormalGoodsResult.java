package com.server.objet.domain.Goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterNormalGoodsResult {
    private final String message;
    private final Long productId;
}
