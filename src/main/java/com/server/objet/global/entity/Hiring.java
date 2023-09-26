package com.server.objet.global.entity;

import com.server.objet.domain.hiring.HiringCall;
import com.server.objet.global.enums.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hiring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hiring_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime localDateTime; //일시
    private String company; //회사명
    private String comment; //내용
    private String contact; //연락처

}