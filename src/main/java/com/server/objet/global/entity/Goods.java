package com.server.objet.global.entity;

import jakarta.persistence.*;
import lombok.*;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long id;

    @Column(name = "a_idx")
    private Long artistId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "goods_type")
    private String type;

    @Column(name = "is_include_delivery_charge")
    private Boolean isInclude;

    @Column(name = "delivery_charge")
    private Long deliveryCharge;

    @Column(name = "price")
    private Long price;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;

    @OneToMany
    @JoinColumn(name = "goods_id")
    private List<GoodsDetail> details;

    @OneToMany
    @JoinColumn(name = "goods_id")
    private List<GoodsThumbNails> thumbNails;

}
