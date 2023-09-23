package com.server.objet.domain.social.service.dto.request;


import com.server.objet.domain.social.enums.SocialPlatform;
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
