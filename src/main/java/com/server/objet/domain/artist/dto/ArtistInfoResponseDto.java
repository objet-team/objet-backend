package com.server.objet.domain.artist.dto;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ArtistInfoResponseDto {

    @Getter
    private final String comment;
    @Getter
    private final String name;
    @Getter
    private final List<Category> categoryList;
    @Getter
    private final String profile;


    @Builder
    public ArtistInfoResponseDto(String comment,String name,List<Category> categoryList,String profile){
        this.name = name;
        this.comment = comment;
        this.profile = profile;
        this.categoryList = categoryList;
    }

}