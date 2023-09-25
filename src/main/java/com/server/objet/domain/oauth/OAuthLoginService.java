package com.server.objet.domain.oauth;

import com.server.objet.domain.oauth.kakao.KakaoTokens;
import com.server.objet.global.entity.User;
import com.server.objet.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository ;
//    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public KakaoTokens login(OAuthLoginParams params) {

        KakaoTokens tokens = requestOAuthInfoService.getTokens(params);

//        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
//        Long userId = findOrCreateMember(oAuthInfoResponse);
//        return authTokensGenerator.generate(userId);
        return tokens;
    }


    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findByEmail(oAuthInfoResponse.getEmail())
                .map(User::getId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user).getId();
    }
}