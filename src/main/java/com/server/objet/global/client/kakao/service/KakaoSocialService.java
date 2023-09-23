package com.server.objet.global.client.kakao.service;

import com.server.objet.domain.social.service.SocialService;
import com.server.objet.global.client.kakao.KakaoApiClient;
import com.server.objet.global.client.kakao.KakaoAuthApiClient;
import com.server.objet.global.client.kakao.service.dto.response.KakaoAccessTokenResponse;
import com.server.objet.global.client.kakao.service.dto.response.KakaoUserResponse;
import com.server.objet.domain.social.entity.SocialUser;
import com.server.objet.domain.social.enums.SocialPlatform;
import com.server.objet.domain.social.repository.SocialUserRepository;
import com.server.objet.domain.social.service.dto.request.SocialLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KakaoSocialService extends SocialService {

    @Value("${kakao.clientId}")
    private String clientId;

    private SocialUserRepository socialUserRepository;

    private KakaoAuthApiClient kakaoAuthApiClient;
    private KakaoApiClient kakaoApiClient;

    @Override
    public Long login(SocialLoginRequest request) {

        System.out.println(clientId);

        // Authorization code로 Access Token 불러오기
        KakaoAccessTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
                "authorization_code",
                clientId,
                "http://localhost:8080/kakao/callback",
                request.getCode()
        );

        // Access Token으로 유저 정보 불러오기
        KakaoUserResponse userResponse = kakaoApiClient.getUserInformation("Bearer " + tokenResponse.getAccessToken());

        SocialUser user = SocialUser.of(
                userResponse.getKakaoAccount().getProfile().getNickname(),
                userResponse.getKakaoAccount().getProfile().getProfileImageUrl(),
                SocialPlatform.KAKAO,
                tokenResponse.getAccessToken(),
                tokenResponse.getRefreshToken()
        );

        socialUserRepository.save(user);

        return user.getId();
    }
}