package com.server.objet.global.repository;

import com.server.objet.global.entity.GoodsDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsDetailRepository extends JpaRepository<GoodsDetail, Long> {
    Optional<GoodsDetail> findTop1ByGoodsIdAndTypeOrderByContentOrderAsc(Long goodsId, String type);
}
