package com.server.objet.domain.auth;

import com.server.objet.domain.auth.kakao.req.KakaoLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;


import static com.server.objet.global.RequestURI.AUTH_URI;

@RequestMapping(AUTH_URI)
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/kakao")
    @Operation(summary = "카카오 로그인", description = "kakao authCode를 넣으면 JWT AccessToken이 나옵니다.")
    public ResponseEntity<?> loginKakao(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(authService.login(kakaoLoginRequest.getAuthorizationCode()));
    }
}
