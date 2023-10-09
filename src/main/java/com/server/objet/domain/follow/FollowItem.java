package com.server.objet.domain.follow;

import com.server.objet.global.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class FollowItem {
    private Long artistId;
    private String artistName; //작가 이름
    private String profileUrl; //작가의 프로필 이미지
    private List<Category> category; //작가의 분야
    private int productNum; //전시 개수
    private int followerNum; //작가의 팔로워수
}
