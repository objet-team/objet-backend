package com.server.objet.global.repository;

import com.server.objet.domain.hiring.HiringDetailResponseDto;
import com.server.objet.global.entity.Hiring;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiringRepository extends JpaRepository<Hiring, Long> {

    List<HiringDetailResponseDto> findByProductId(Long productId);
}