package com.example.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers("/movie/delete").hasAuthority("admin")
                        .requestMatchers("/movie/update").authenticated()
                        .requestMatchers("/movie/create").authenticated()
                        .anyRequest().permitAll()
                );
        http.formLogin(formLogin ->
                formLogin.defaultSuccessUrl("/movie"));
        http.logout(logout ->
                logout.logoutSuccessUrl("/movie"));
        return http.build();
    }
}
