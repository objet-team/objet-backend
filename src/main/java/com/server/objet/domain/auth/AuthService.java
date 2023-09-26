package com.server.objet.domain.auth;


import com.server.objet.domain.auth.jwt.JwtService;
import com.server.objet.domain.auth.kakao.OAuthService;
import com.server.objet.domain.auth.kakao.res.KakaoInfoResponse;
import com.server.objet.domain.auth.res.PostLoginRes;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.Role;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final OAuthService oAuthService;
    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Transactional
    public PostLoginRes login(String authorizationCode){
        String accessToken = oAuthService.requestAccessToken(authorizationCode);
        KakaoInfoResponse kakaoInfoResponse =oAuthService.requestOauthInfo(accessToken);
        String email=kakaoInfoResponse.getKakaoAccount().getEmail();
        User user = userRepository.findByEmail(email).orElseGet(
                ()->saveUser(kakaoInfoResponse)
        );
        String serviceAccessToken= jwtService.createAccessToken(email);

        return PostLoginRes.builder()
                .accessToken(serviceAccessToken)
                .build();
    }

    @Transactional

    public User saveUser(KakaoInfoResponse kakaoInfoResponse){

       User member = User.builder()
                .email(kakaoInfoResponse.getKakaoAccount().getEmail())
                .role(Role.USER)
                .name(kakaoInfoResponse.getKakaoAccount().getProfile().getNickname())
                .build();
        userRepository.saveAndFlush(member);
        return  member;

    }
}
