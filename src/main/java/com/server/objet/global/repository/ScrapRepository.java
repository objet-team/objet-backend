package com.server.objet.global.repository;

import com.server.objet.global.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    int countByUserId(Long userId);

    List<Scrap> findByUserId(Long userId);

    Optional<Scrap> findByUserIdAndProductId(Long userId, Long productId);
}
