package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Product {

    // ToDo: erd와 달라진 사항들 존재

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

    @OneToMany
    @JoinColumn(name = "p_id")
    private List<Image> images;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;

}
