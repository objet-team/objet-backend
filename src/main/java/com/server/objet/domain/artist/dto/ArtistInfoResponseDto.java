package com.server.objet.domain.artist.dto;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.enums.Category;

import java.util.List;

public class ArtistInfoResponseDto {

    private final String user;
    private final String comment;
    private final List<Category> categoryList;

    private final String profile;

    public ArtistInfoResponseDto(Artist artist){
        comment = artist.getComment();
        profile = artist.getProfilePicUrl();
        categoryList = artist.getCategory();
        user = artist.getUser().getEmail();
    }
}