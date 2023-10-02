package com.server.objet.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class MainPageGoods {
    private List<MainPageGoodsInfo> GoodsInfos;
}
