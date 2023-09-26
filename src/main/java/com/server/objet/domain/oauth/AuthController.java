package com.server.objet.domain.oauth;

import com.server.objet.domain.auth.AuthTokens;
import com.server.objet.domain.auth.CreateJwt;
import com.server.objet.domain.auth.TokenResponseDto;
import com.server.objet.domain.oauth.kakao.KakaoInfoResponse;
import com.server.objet.domain.oauth.kakao.KakaoLoginParams;
import com.server.objet.global.RequestURI;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.OAuthProvider;
import com.server.objet.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.AUTH_URI)
public class AuthController {
    private final OAuthLoginService oAuthLoginService;
    private final UserRepository userRepository;

//    @PostMapping("/kakao")
//    public ResponseEntity<TokenResponseDto> loginKakao(@RequestBody KakaoLoginParams params) {
//        return ResponseEntity.ok(oAuthLoginService.login(params));
//    }

    @PostMapping("/kakao")
    public JwtReturner jwtCreate(@RequestBody KakaoLoginParams params) {

//        OAuthInfoResponse KakaoInfo =
//                new KakaoInfoResponse(); // 프론트가 보내준 json 받기

//        User user = userRepository.findByEmail(KakaoInfo.getEmail());

        OAuthInfoResponse kakaoUser =oAuthLoginService.findInfo(params);
        System.out.println(kakaoUser.getEmail());


        //        String provider = googleUser.getProvider();
//        String providerId = kakaoUser.getProviderId();
//        String name = kakaoUser.getName();
//        String username = providerId + "_" + name;
//        String password = kakaoUser.encode("CommonPassword");
//        String email = kakaoUser.getEmail();
//        MemberDetails memberDetails = new MemberDetails();

//        if (user == null) {
            User userEntity = User.builder()
                    .name(kakaoUser.getNickname())
                    .email(kakaoUser.getEmail())
                    .oAuthProvider(OAuthProvider.KAKAO)
                    .build();
            userRepository.save(userEntity);
//        }

        String accessToken = CreateJwt.createAccessToken(userEntity);
        String refreshToken = CreateJwt.createRefreshToken(userEntity, accessToken);

//        JwtReturner returner = CreateTokens.createAccessToken(memberEntity);

        return new JwtReturner(accessToken, refreshToken);
    }

}