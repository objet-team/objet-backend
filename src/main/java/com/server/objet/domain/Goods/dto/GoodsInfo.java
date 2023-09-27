package com.server.objet.domain.Goods.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Data
public class GoodsInfo {
    private String title;
    private String category;
    private String description;
    private List<Object> goodsDetails;
}
