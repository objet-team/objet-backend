package com.server.objet.domain.hiring;

import com.server.objet.global.entity.Hiring;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

public class HiringCall {
    LocalDateTime localDateTime; //일시
    String company; //회사명
    String comment; //내용
    String contact; //연락처

    @Builder
    public HiringCall(LocalDateTime localDateTime,
                      String contact, String company, String comment){
        this.localDateTime = localDateTime;
        this.comment = comment;
        this.company = company;
        this.contact = contact;
    }

    public HiringCall(Hiring hiring){
        contact = hiring.getContact();
        company = hiring.getCompany();
        comment = hiring.getComment();
        localDateTime = hiring.getLocalDateTime();
    }
}
