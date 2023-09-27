package com.server.objet.global.entity;

import com.server.objet.global.enums.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "a_idx")
    private Collection<Product> products;

    @OneToMany
    @JoinColumn(name = "a_idx")
    private Collection<Goods> goods;

    @Column(name = "pic_url")
    private String profilePicUrl;

    @Enumerated(EnumType.STRING)
    private List<Category> category;

    @Builder
    public Artist(String comment, User user, List<Category> category){
        this.user = user;
        this.comment = comment;
        this.category = category;
    }

    public void update(String comment, List<Category> category){
        this.comment = comment;
        this.category = category;
    }
}