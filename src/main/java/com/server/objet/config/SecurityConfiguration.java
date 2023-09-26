package com.server.objet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.objet.domain.auth.AuthTokensGenerator;
import com.server.objet.domain.auth.CustomUserDetailsService;
import com.server.objet.domain.oauth.CustomOAuthLoginFilter;
import com.server.objet.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthTokensGenerator authTokensGenerator;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**"
                        , "/sign/**", "/product/**", "/auth/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable))

                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));



        http.addFilterAfter(customOAuthLoginFilter(), LogoutFilter.class);
//        http.addFilterAfter(customLoginFilter(), CustomOAuthLoginFilter.class);
//        http.addFilterBefore(jwtAuthenticationFilter(), CustomLoginFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CustomOAuthLoginFilter customOAuthLoginFilter() {
        CustomOAuthLoginFilter customOAuthLoginFilter = new CustomOAuthLoginFilter(objectMapper,
                authenticationManager());

        customOAuthLoginFilter.setAuthenticationManager(authenticationManager());
//        customOAuthLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
//        customOAuthLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);

        return customOAuthLoginFilter;
    }

}
