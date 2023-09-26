package com.server.objet.domain.auth.jwt;




import com.server.objet.domain.auth.CustomUserDetails;
import com.server.objet.global.entity.User;
import com.server.objet.global.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractUuid(accessToken)
                        .ifPresent(id->userRepository.findByEmail(id)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User user) {

        CustomUserDetails customUserDetail=new CustomUserDetails(user);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(customUserDetail, null,
                        authoritiesMapper.mapAuthorities(customUserDetail.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String NO_CHECK_URL = "/api/v1/auth/kakao";
        if (request.getRequestURI().equals(NO_CHECK_URL)) {
            log.info("로그인 진입");
            filterChain.doFilter(request, response);
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }

        log.info("로그인 이외 페이지 진입");

        checkAccessTokenAndAuthentication(request, response, filterChain);

    }
}