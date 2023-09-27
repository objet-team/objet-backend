package com.server.objet.domain.hiring;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class HiringRequestDto {
    String company; //회사명
    String comment; //내용
    String contact; //연락처
}
