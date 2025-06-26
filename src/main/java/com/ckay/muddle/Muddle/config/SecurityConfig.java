package com.ckay.muddle.Muddle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF protection
                .authorizeHttpRequests(auth -> auth // all requests don't need authentication
                        .anyRequest().permitAll()
                );
        return http.build(); // calling .build to finalize security filter chain
    }
}

