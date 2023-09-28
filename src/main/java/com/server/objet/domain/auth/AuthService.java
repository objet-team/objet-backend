package com.server.objet.domain.auth;


import com.server.objet.domain.auth.jwt.JwtService;
import com.server.objet.domain.auth.kakao.OAuthService;
import com.server.objet.domain.auth.kakao.res.KakaoInfoResponse;
import com.server.objet.domain.auth.res.PostLoginRes;
import com.server.objet.global.entity.User;
import com.server.objet.global.enums.OAuthProvider;
import com.server.objet.global.enums.Role;
import com.server.objet.global.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final OAuthService oAuthService;
    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Transactional
    public LoginResponseDto login(String authorizationCode){
        String accessToken = oAuthService.requestAccessToken(authorizationCode);
        KakaoInfoResponse kakaoInfoResponse =oAuthService.requestOauthInfo(accessToken);
        String email=kakaoInfoResponse.getKakaoAccount().getEmail();
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseGet(
                ()->saveUser(kakaoInfoResponse)
        );
        String serviceAccessToken= jwtService.createAccessToken(email);


        return LoginResponseDto.builder()
                .accessToken(serviceAccessToken)
                .userId(user.getId())
                .role(user.getRole())
                .userName(user.getName())
                .build();
    }

    @Transactional
    public User saveUser(KakaoInfoResponse kakaoInfoResponse){

        User member = User.builder()
                .oAuthProvider(OAuthProvider.KAKAO)
                .email(kakaoInfoResponse.getKakaoAccount().getEmail())
                .role(Role.USER)
                .name(kakaoInfoResponse.getKakaoAccount().getProfile().getNickname())
                .build();
        userRepository.saveAndFlush(member);
        return  member;

    }

    @Transactional
    public MyInfoResponseDto getMyInfo(CustomUserDetails userDetails){
        User user = userRepository.findByEmail(userDetails.getEmail())
                .orElseThrow(() ->new UsernameNotFoundException("사용자를 찾을 수 없습니다"));

        //Todo 프로필 이미지 넣어야함
        return  MyInfoResponseDto.builder()
                .name(userDetails.getUsername())
                .email(user.getEmail())
                .followingArtistNum(user.getFollows().size())
                .build();
    }
}
