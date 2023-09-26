package com.server.objet.domain.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.server.objet.global.entity.User;

public class CreateJwt {

    public static String createAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getName())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
//                .withExpiresAt(new Date(System.currentTimeMillis() + 60000*10))
                .withClaim("id", user.getId())
                .withClaim("username", user.getName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public static String createRefreshToken(User userEntity, String AccessToken) {
        return JWT.create()
                .withSubject(userEntity.getName())
//                .withExpiresAt(new Date(System.currentTimeMillis()+ 60000*100))
                .withClaim("AccessToken", AccessToken)
                .withClaim("username", userEntity.getName())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
