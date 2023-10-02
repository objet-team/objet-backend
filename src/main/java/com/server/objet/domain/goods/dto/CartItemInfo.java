package com.server.objet.domain.goods.dto;

import com.server.objet.global.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class CartItemInfo {
    private Long cartId;
    private Long artistId;
    private String artistName;
    private Long goodsId;
    private String goodsName;
    private Boolean isInclude;
    private Long deliveryCharge;
    private Long price;
    private Integer cnt;
    private LocalDateTime createAt;
}
