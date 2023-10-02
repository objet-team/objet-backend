package com.server.objet.domain.scrap;

import com.server.objet.global.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ScrapItem {
    private Long productId;
    private String productName;

    private Long artistId;
    private String artistName;

    private List<Category> category;

    private Long LikeNum;

    private String thumbnail;
}
