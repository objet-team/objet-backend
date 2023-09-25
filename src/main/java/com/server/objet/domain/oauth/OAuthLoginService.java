package com.server.objet.domain.oauth;

import com.server.objet.domain.oauth.kakao.KakaoTokens;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.Role;
import com.server.objet.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository ;

    private final RequestOAuthInfoService requestOAuthInfoService;

    public KakaoTokens login(OAuthLoginParams params) {

        KakaoTokens tokens = requestOAuthInfoService.getTokens(params);
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(tokens,params);
        Long userId = findOrCreateMember(oAuthInfoResponse,tokens);

        System.out.println(userId);
        System.out.println(tokens.getAccessToken());

        return tokens;
    }


    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse, KakaoTokens tokens) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse, tokens));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse, KakaoTokens tokens) {
        User user = User.builder()
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                .role(Role.USER)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user).getId();
    }
}