package com.server.objet.domain.oauth;

import com.server.objet.domain.oauth.kakao.KakaoTokens;
import com.server.objet.global.enums.OAuthProvider;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestOAuthInfoService {
    private final Map<OAuthProvider, OAuthApiClient> clients;

    public RequestOAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }

//    public OAuthInfoResponse request(OAuthLoginParams params) {
//        OAuthApiClient client = clients.get(params.oAuthProvider());
//        KakaoTokens tokens = client.requestTokens(params);
////        String accessToken = client.requestAccessToken(params);
//        return client.requestOauthInfo(tokens.getAccessToken());
//    }

    public KakaoTokens getTokens(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());
        KakaoTokens token = client.requestTokens(params);
//        token.getAccessToken();

        return token;
    }
}