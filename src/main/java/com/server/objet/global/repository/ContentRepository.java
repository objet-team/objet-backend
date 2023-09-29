package com.server.objet.global.repository;

import com.server.objet.global.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findTop1ByProductIdAndTypeOrderByContentOrderAsc(Long productId, String type);
    void deleteByProductId(Long productId);
}
