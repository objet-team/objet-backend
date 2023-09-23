package com.server.objet.domain.social;


import com.server.objet.domain.social.service.SocialService;
import com.server.objet.domain.social.service.SocialServiceProvider;
import com.server.objet.domain.social.service.dto.request.SocialLoginRequest;
import com.server.objet.domain.social.service.dto.request.SocialLoginRequestDto;
import com.server.objet.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/social")
public class SocialController {

    private SocialServiceProvider socialServiceProvider;

    @PostMapping("/login")
    public ResponseDto<Long> login(@RequestHeader("code") String code, @RequestBody SocialLoginRequestDto request) {
        SocialService socialService = socialServiceProvider.getSocialService(request.getSocialPlatform());
        return ResponseDto.success("로그인 성공", socialService.login(SocialLoginRequest.of(code)));
    }


}