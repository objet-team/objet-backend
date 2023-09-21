package com.server.objet.global.entity;

import com.server.objet.global.enums.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

    private String comment;

    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Artist(String comment, Category category, User user) {
        this.comment = comment;
        this.category = category;
        this.user = user;
    }

    public void update(String comment, Category category) {
        this.comment = comment;
        this.category = category;
    }
}