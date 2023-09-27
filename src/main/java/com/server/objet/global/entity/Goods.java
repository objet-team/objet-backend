package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "a_idx")
    private Long artistId;

    @Column(name = "name")
    private String name;

    @Column(name = "goods_type")
    private String Type;

    @Column(name = "price")
    private Long price;

    @OneToMany
    @JoinColumn(name = "goods_id")
    private List<GoodsDetail> details;
}
