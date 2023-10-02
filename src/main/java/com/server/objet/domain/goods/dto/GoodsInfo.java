package com.server.objet.domain.goods.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GoodsInfo {
    private String title;
    private String category;
    private String description;
    private Boolean isInclude;
    private Long deliveryCharge;
    private Long price;
    private List<ThumbNailInfo> thumbnails;
    private List<Object> goodsDetails;
}
