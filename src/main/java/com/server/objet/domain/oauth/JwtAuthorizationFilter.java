package com.server.objet.domain.oauth;

import java.io.IOException;
import java.util.*;


import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.objet.domain.auth.CreateJwt;
import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.domain.auth.JwtProperties;
import com.server.objet.global.RequestURI;
import com.server.objet.global.entity.User;
import com.server.objet.global.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String[] NO_CHECK_URI_ARRAY = {
//            "/auth/**",
            //토큰 필요 없는 것들은 여기에서 설정해야 함

            RequestURI.USER_URI + "/login",
            RequestURI.USER_URI + "/signup",
            RequestURI.USER_URI + "/oauth-login",
            RequestURI.USER_URI + "/verify-user-email",

            RequestURI.AUTH_URI + "/kakao",
            "/auth/kakao",
            "/auth/**",

            RequestURI.EMAIL_URI + "/send",
            RequestURI.EMAIL_URI + "/certify",

            RequestURI.ADVERTISEMENT_URI + "/get-advertisement",

            RequestURI.POLICY_URI + "/privacy-policy",
            RequestURI.POLICY_URI + "/terms-of-use",

            "/swagger-ui/index.html",
            "/favicon.ico"
    };
    private static final List<String> NO_CHECK_URIS = new ArrayList<>(
            Arrays.asList(NO_CHECK_URI_ARRAY));
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (NO_CHECK_URIS.contains(request.getRequestURI())
                || request.getRequestURI().contains("/swagger-ui")
                || request.getRequestURI().contains("/v3")) {
            chain.doFilter(request, response);
            return;
        }

        String access_token = request.getHeader("ACCESS_TOKEN");
        String refresh_token = request.getHeader("REFRESH_TOKEN");

//        //header에 있는 jwt bearer 토큰 검증
//        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        //bearer 부분 자르기
//        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
        // 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
        // loadByUsername이 호출됨.
        String username = null;
        String restoreAccessToken = null;

        try {
            username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(access_token)
                    .getClaim("username").asString();

            restoreAccessToken = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refresh_token)
                    .getClaim("AccessToken").asString();

        } catch (TokenExpiredException e) {
            String restoreUsername = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refresh_token)
                    .getClaim("username").asString();
            if (restoreUsername != null && restoreAccessToken == access_token) {
                User user = userRepository.findByEmail(restoreUsername);
                String accessToken = CreateJwt.createAccessToken(user);
                String refreshToken = CreateJwt.createRefreshToken(user, accessToken);

                response.setHeader("ACCESS_TOKEN", accessToken);
                response.setHeader("REFRESH_TOKEN", refreshToken);
            }
        }

        if (username != null) {
            User user = userRepository.findByName(username);
            System.out.println("user: "+username); //이메일

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            CustomUserDetails principalDetails = new CustomUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
                    null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}