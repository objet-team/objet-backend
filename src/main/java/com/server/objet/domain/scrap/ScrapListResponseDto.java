package com.server.objet.domain.scrap;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScrapListResponseDto {
    private int totalScrapNum;
    private List<ScrapItem> scrapItems;
}
