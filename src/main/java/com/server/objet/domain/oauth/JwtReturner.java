package com.server.objet.domain.oauth;

import lombok.Data;

@Data
public class JwtReturner {

    private String accessToken;
    private String RefToken;

    public JwtReturner(String accessToken, String refToken) {
        this.accessToken = accessToken;
        RefToken = refToken;
    }
}