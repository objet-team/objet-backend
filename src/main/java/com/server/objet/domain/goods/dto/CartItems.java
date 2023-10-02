package com.server.objet.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartItems {
    private Long totalPrice;
    private List<CartItemInfo> cartItemInfos;
}
