package com.server.objet.domain.artist.dto;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.enums.Category;
import lombok.Builder;

import java.util.List;

public class ArtistInfoResponseDto {

    private final String comment;
    private final String name;
    private final List<Category> categoryList;

    private final String profile;


    @Builder
    public ArtistInfoResponseDto(String comment,String name,List<Category> categoryList,String profile){
        this.name = name;
        this.comment = comment;
        this.profile = profile;
        this.categoryList = categoryList;
    }
}