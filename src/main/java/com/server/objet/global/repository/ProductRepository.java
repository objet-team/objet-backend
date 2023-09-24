package com.server.objet.global.repository;

import com.server.objet.global.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    List<Product> findTop12ByOrderByUploadAtDesc();
}
