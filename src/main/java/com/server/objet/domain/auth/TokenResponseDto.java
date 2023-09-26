package com.server.objet.domain.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponseDto {
    private final String accessToken;
    private final String refreshToken;
}