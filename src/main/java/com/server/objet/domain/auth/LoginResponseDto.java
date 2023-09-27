package com.server.objet.domain.auth;

import com.server.objet.global.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String accessToken;
    private Role role;
    private Long userId;
    private String userName;
}
