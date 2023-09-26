package com.server.objet.domain.auth;

import com.server.objet.domain.auth.kakao.req.KakaoLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(authService.login(kakaoLoginRequest.getAuthorizationCode()));
    }
}
