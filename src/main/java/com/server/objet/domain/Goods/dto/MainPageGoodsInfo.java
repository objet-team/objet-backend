package com.server.objet.domain.Goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MainPageGoodsInfo {
    private Integer rank;
    private Long goodsId;
    private String title;
    private String category;
    private Long price;
    private String artistName;
    private String artistPicPath;
    private String thumbNailPath;
}
