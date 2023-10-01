package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GoodsThumbNails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thumbnail_id")
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;

    @Column(name = "content_order")
    private Integer contentOrder;

    @Column(name = "url")
    private String url;

}
