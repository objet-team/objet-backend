package com.server.objet.domain.goods.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CartRegisterInfo {
    private Long goodsId;
    private Integer count;
}
