package com.server.objet.domain.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.objet.domain.auth.AuthDomain;
import com.server.objet.domain.oauth.kakao.KakaoApiClient;
import com.server.objet.domain.oauth.kakao.KakaoLoginParams;
import com.server.objet.global.RequestURI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//@Component
public class CustomOAuthLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URI = RequestURI.AUTH_URI + "/kakao";
    private static final String HTTP_METHOD = "POST";
    private final AuthDomain authDomain;


    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URI, HTTP_METHOD);

    public CustomOAuthLoginFilter(ObjectMapper objectMapper,
                                  AuthenticationManager authenticationManager) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        authDomain = new AuthDomain(objectMapper, authenticationManager);
    }
    RequestOAuthInfoService requestOAuthInfoService;

//    @Autowired
    OAuthLoginService oAuthLoginService;

    KakaoApiClient kakaoApiClient;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, IOException {
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        System.out.println("request"+body);

        KakaoLoginParams kakaoLoginParams = new KakaoLoginParams(body);
        System.out.println("request"+kakaoLoginParams);
//        OAuthInfoResponse oAuthInfoResponse = oAuthLoginService.findInfo(kakaoLoginParams);

        String access = kakaoApiClient.requestAccessToken(kakaoLoginParams);
        OAuthInfoResponse info = kakaoApiClient.requestOauthInfo(access);
        System.out.println(info.getEmail());

    //        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(kakaoLoginParams);
        return authDomain.authentication(request.getContentType(), info);
    }
}