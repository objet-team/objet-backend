package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "artist_id")
    private Long artistId;


    @Builder
    public Follow(Long userId, Long artistId){
        this.artistId = artistId;
        this.userId =userId;
    }
}