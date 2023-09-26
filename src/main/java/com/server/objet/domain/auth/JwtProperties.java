package com.server.objet.domain.auth;

import org.springframework.beans.factory.annotation.Value;

public interface JwtProperties {

//    @Value("${security.jwt.token.secret}")
    String SECRET = "07916abaaa78301a4dab1c5a848bd968fef847cea3ce658f2356202bfe2690cfac9c672e0ab249baa18bbea23076f01a75ef9056d9c4bcdb968c8c363f93be27"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 60000*10; // 10분
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}