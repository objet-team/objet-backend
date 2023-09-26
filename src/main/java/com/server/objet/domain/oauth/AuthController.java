package com.server.objet.domain.oauth;

import com.server.objet.domain.auth.AuthTokens;
import com.server.objet.domain.auth.TokenResponseDto;
import com.server.objet.domain.oauth.kakao.KakaoLoginParams;
import com.server.objet.global.RequestURI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(RequestURI.AUTH_URI)
public class AuthController {
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<TokenResponseDto> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

}