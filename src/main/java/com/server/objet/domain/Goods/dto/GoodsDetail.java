package com.server.objet.domain.Goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class GoodsDetail {
    private Long goodsId;
    private String title;
    private String category;
    private String detail;
    private Long like;
    private String artistName;
    private String artistInfo;
    private String artistPicPath;
    private List<Object> contents;
}
