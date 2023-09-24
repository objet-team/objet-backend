package com.server.objet.global.repository;

import com.server.objet.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    /*
    * 레포지토리들이 모여있는 패키지입니다. 디펜던시에 JPA를 추가한 후 작업해주세요.
    * */
}
