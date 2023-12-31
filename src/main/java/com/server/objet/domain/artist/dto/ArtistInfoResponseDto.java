package com.server.objet.domain.artist.dto;

import com.server.objet.global.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ArtistInfoResponseDto {

    private final Long id;
    private final String name;
    private final String comment;
    private final List<Category> categoryList;
    private final String profilePrcUrl;
}