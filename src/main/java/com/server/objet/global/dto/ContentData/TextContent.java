package com.server.objet.global.dto.ContentData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TextContent {
    private Long id;
    private String type;
    private Integer order;
    private String sizeType;
    private String description;
    private String align;

}
