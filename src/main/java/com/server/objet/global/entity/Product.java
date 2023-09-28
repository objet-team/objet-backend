package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "p_idx")
    private Long id;

    @Column(name = "a_idx")
    private Long artistId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String desc;

    @Column(name = "category")
    private String category;

    @Column(name = "like_count")
    private Long likeCount;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;

    @OneToMany
    @JoinColumn(name = "p_id")
    private List<Content> contents;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Like> likes;

    public void updateLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

}
