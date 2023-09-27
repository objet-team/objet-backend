package com.server.objet.domain.hiring;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HiringDetailResponseDto {

    LocalDateTime localDateTime; //일시
    String company; //회사명
    String comment; //내용
    String contact; //연락처

    @Builder
    public HiringDetailResponseDto(LocalDateTime localDateTime,
                      String contact, String company, String comment){
        this.localDateTime = localDateTime;
        this.comment = comment;
        this.company = company;
        this.contact = contact;
    }
}
