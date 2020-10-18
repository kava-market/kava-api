package com.kava.market.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http
                .cors() // Bypass preflight
                .and()
                .csrf().disable() // May mandate this
                .oauth2Login()
                .and()
                .authorizeExchange()
                .pathMatchers("/health")
                .permitAll()
                .anyExchange()
                .authenticated()
                .and()
                .build();
    }
}
