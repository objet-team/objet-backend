package com.server.objet.domain.social.service;


import com.server.objet.domain.social.service.dto.request.SocialLoginRequest;

public abstract class SocialService {
    public abstract Long login(SocialLoginRequest request);

    public void logout(Long userId) {
        // 아쩌고 저쩌고 로직
    }
}