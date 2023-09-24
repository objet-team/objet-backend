package com.server.objet.global.repository;

import com.server.objet.global.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByProductIdAndOrder(Long productId, Integer order);
    List<Image> findByProductId();
}
