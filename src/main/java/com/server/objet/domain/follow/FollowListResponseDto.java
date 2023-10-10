package com.server.objet.domain.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowListResponseDto {
    private int totalFollowingNum;
    private List<FollowItem> followItems;
}