package com.server.objet.global.dto.ContentData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SpaceContent {
    private Long id;
    private String type;
    private Integer order;

}
