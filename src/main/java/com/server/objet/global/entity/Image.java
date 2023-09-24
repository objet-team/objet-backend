package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column(name = "p_id")
    private Long productId;

    @Column(name = "img_order")
    private Integer order;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "xCor")
    private Double xCor;

    @Column(name = "yCor")
    private Double yCor;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;
}
