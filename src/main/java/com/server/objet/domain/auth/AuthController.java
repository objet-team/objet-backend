package com.server.objet.domain.auth;

import com.server.objet.domain.auth.kakao.req.KakaoLoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.hibernate.annotations.Synchronize;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import static com.server.objet.global.RequestURI.AUTH_URI;

@RequestMapping(AUTH_URI)
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/kakao")
    @Synchronized
    @Operation(summary = "카카오 로그인", description = "kakao authCode를 넣으면 JWT AccessToken이 나옵니다. isLocal은 true이면 로컬 주소입니다.")
    public synchronized ResponseEntity<?> loginKakao(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(authService.login(kakaoLoginRequest.getAuthorizationCode(), kakaoLoginRequest.getIsLocal()));
    }

    @GetMapping("/user/info")
    @Operation(summary = "유저 마이페이지", description = "토큰이 필요합니다. 팔로우하는 작가 수와 프로필 이미지는 현재 제공하지 않습니다.")
    public ResponseEntity<MyInfoResponseDto> MyInfo(@AuthenticationPrincipal CustomUserDetails userDetail) {
        return ResponseEntity.ok(authService.getMyInfo(userDetail));
    }
}
