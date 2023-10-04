package com.server.objet.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartGoodsRecount {
    private Long cartId;
    private Integer newCount;
}
