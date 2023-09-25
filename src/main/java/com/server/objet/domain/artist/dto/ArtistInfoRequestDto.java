package com.server.objet.domain.artist.dto;

import com.server.objet.global.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class ArtistInfoRequestDto {

    String comment;
    List<Category> categoryList;
}