package com.server.objet.domain.oauth;

import com.server.objet.domain.auth.AuthTokens;
import com.server.objet.domain.auth.AuthTokensGenerator;
import com.server.objet.domain.auth.TokenResponseDto;
import com.server.objet.domain.oauth.kakao.KakaoTokens;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.Role;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository ;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
//    private AuthTokens tokens;


    public TokenResponseDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);

//        System.out.println(tokens.getAccessToken());
//
//        User user = User.builder()
//                .email(oAuthInfoResponse.getEmail())
//                .name(oAuthInfoResponse.getNickname())
//                .role(Role.USER)
//                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
//                .accessToken(tokens.getAccessToken())
//                .refreshToken(tokens.getRefreshToken())
//                .build();

//        user.update(tokens.getAccessToken(),tokens.getRefreshToken());

//        System.out.println("user"+user.getAccessToken());

        return getTokens(memberId);
    }


    @Transactional
    protected TokenResponseDto getTokens(Long memberId) {
        String accessToken = authTokensGenerator.generate(memberId).getAccessToken();
        String refreshToken = authTokensGenerator.generate(memberId).getAccessToken();

//        user.updateRefreshToken(refreshToken);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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
//                .role(Role.USER)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
//                .accessToken(tokens.getAccessToken())
//                .refreshToken(tokens.getRefreshToken())
                .build();

        return userRepository.save(user).getId();
    }

    public OAuthInfoResponse findInfo(OAuthLoginParams params){
        return requestOAuthInfoService.request(params);
    }
}