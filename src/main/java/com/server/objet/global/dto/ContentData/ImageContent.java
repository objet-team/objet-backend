package com.server.objet.global.dto.ContentData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ImageContent {
    private Long id;
    private String type;
    private Integer order;
    private String url;
    private String align;
    private Long width;
    private Long height;
}
