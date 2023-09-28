package com.server.objet.domain.artist.dto;

import com.server.objet.global.enums.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MyArtistInfoResponseDto {

    private final String name;
    private final String comment;
    private final List<Category> categoryList;
    private final String profilePrcUrl;

    private final int productNum; //전시 개수
    private final int followingNum; //유저로서 내가 팔로잉 하는 수
    private final int followerNum; //작가인 나를 팔로워하는 사람의 수
}