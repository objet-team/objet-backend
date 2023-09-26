package com.server.objet.domain.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.objet.domain.oauth.OAuthInfoResponse;
import com.server.objet.global.entity.User;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


@RequiredArgsConstructor
public class AuthDomain {
    private static final List<String> CONTENT_TYPE = Arrays.asList(
            "application/json",
            "application/json; charset=UTF-8",
            "application/json;charset=UTF-8");

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    public Authentication authentication(String contentType, OAuthInfoResponse infoResponse)
            throws JsonProcessingException {
        // 여기서 request의 body를 ObjectMapper로 읽고 로그인 처리를 해주는 것!

        // contentType이 기재되지 않았거나 application/json이 아니면 에러를 던진다.
        if (contentType == null || !CONTENT_TYPE.contains(contentType)) {
            throw new AuthenticationServiceException(
                    "지원되지 않는 Content-Type입니다. " + contentType);
        }

//        OAuthInfoResponse mappedBody = objectMapper.readValue(jsonBody, OAuthInfoResponse.class);


        String email = infoResponse.getEmail();
        //Todo 여기 좀 걸림

        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(email,
                null);
        return authenticationManager.authenticate(authReq);
    }
}