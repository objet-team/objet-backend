package com.server.objet.global.repository;

import com.server.objet.global.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface FollowingRepository extends JpaRepository<Follow, Long> {
    int countByUserId(Long userId);
    int countByArtistId(Long artistId);

    Optional<Follow> findByUserIdAndArtistId(Long userId, Long artistId);
}
