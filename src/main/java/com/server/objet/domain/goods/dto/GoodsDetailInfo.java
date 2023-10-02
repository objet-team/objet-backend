package com.server.objet.domain.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class GoodsDetailInfo {
    private Long goodsId;
    private String name;
    private String category;
    private String description;
    private Boolean isInclude;
    private Long deliveryCharge;
    private Long price;
    private String artistName;
    private String artistInfo;
    private String artistPicPath;
    private List<ThumbNailInfo> thumbnails;
    private List<Object> contents;
}
