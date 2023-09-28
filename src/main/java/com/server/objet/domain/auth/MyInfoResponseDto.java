package com.server.objet.domain.auth;

import com.server.objet.global.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MyInfoResponseDto {

    @Getter
    private final String name;

    @Getter
    private final String profile;

    @Getter
    private final int followingArtistNum;

    @Getter
    private final String email;


    @Builder
    public MyInfoResponseDto(String name, String profile, int followingArtistNum, String email){
        this.name = name;
        this.followingArtistNum = followingArtistNum;
        this.profile = profile;
        this.email = email;
    }
}
