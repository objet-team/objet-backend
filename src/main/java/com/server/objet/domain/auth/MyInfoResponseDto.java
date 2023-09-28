package com.server.objet.domain.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
public class MyInfoResponseDto {

    private final String name;
    private final String profile;
    private final String email;
    private final int followingNum;
}
