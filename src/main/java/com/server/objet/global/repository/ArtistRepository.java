package com.server.objet.global.repository;

import com.server.objet.global.entity.Artist;
import com.server.objet.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
//    Optional<T> findById(ID id);

    Artist findByUserId(Long userId);

    Optional<Artist> findByUser(User user);

    Optional<Artist> findById(Long artistId);
}
