package com.server.objet.global.repository;

import com.server.objet.global.entity.GoodsThumbNail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodsThumbNailRepository extends JpaRepository<GoodsThumbNail, Long> {
    Optional<GoodsThumbNail> findByGoodsIdAndAndContentOrder(Long goodsId, Long id);
}
