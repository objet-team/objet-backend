package com.server.objet.domain.kakao.service.dto.request;


import com.server.objet.domain.kakao.enums.SocialPlatform;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialLoginRequestDto {

    private SocialPlatform socialPlatform;

    public static SocialLoginRequestDto of(SocialPlatform socialPlatform) {
        return new SocialLoginRequestDto(socialPlatform);
    }
}
