package com.server.objet.domain.social.repository;

import com.server.objet.domain.social.entity.SocialUser;
import org.springframework.data.repository.Repository;

public interface SocialUserRepository extends Repository<SocialUser, Long> {

    // CREATE
    void save(SocialUser socialUser);
}