package com.server.objet.global.repository;

import com.server.objet.global.entity.Goods;
import com.server.objet.global.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findTop12ByTypeOrderByUploadAtDesc(String type);
}
