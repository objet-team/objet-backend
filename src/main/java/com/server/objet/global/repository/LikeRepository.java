package com.server.objet.global.repository;

import com.server.objet.global.entity.Like;
import com.server.objet.global.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndProductId(Long userId, Long productId);
    Long countByUserIdAndProductId(Long userId, Long productId);
    void deleteByProductId(Long productId);
}
