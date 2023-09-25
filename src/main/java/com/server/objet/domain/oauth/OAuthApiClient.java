package com.server.objet.domain.oauth;

import com.server.objet.domain.oauth.kakao.KakaoTokens;
import com.server.objet.global.enums.OAuthProvider;

public interface OAuthApiClient {
    OAuthProvider oAuthProvider();

    KakaoTokens requestTokens(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);
}