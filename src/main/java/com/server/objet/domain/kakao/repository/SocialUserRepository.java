package com.server.objet.domain.kakao.repository;

import com.server.objet.domain.kakao.entity.SocialUser;
import org.springframework.data.repository.Repository;

public interface SocialUserRepository extends Repository<SocialUser, Long> {

    // CREATE
    void save(SocialUser socialUser);
}