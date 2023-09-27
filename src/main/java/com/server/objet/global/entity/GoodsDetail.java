package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoodsDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

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
