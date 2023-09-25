package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @Column(name = "p_id")
    private Long productId;

    @Column(name = "content_type")
    private String type;

    @Column(name = "content_order")
    private Integer contentOrder;

    @Column(name = "size_type")
    private String sizeType;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "align")
    private String align;

    @Column(name = "width")
    private Long width;

    @Column(name = "height")
    private Long height;

}
