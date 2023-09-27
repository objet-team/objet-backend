package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hiring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiring_id")
    private Long id;

    @Column (name = "product_id")
    private Long productId;

    @Column (name = "user_id")
    private Long userId;

    private LocalDateTime localDateTime; //일시
    private String company; //회사명
    private String comment; //내용
    private String contact; //연락처

    @Builder
    public Hiring(Long productId, Long userId, LocalDateTime localDateTime, String comment, String company, String contact){
        this.comment = comment;
        this.company = company;
        this.contact = contact;
        this.productId = productId;
        this.userId = userId;
        this.localDateTime =localDateTime;
    }

}