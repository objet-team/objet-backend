package com.server.objet.domain.auth;

import com.server.objet.global.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyInfoChangeRequestDto {
    String name;
    String profilePicUrl;
}
