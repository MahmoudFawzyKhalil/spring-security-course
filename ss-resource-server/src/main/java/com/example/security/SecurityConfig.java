package com.example.security;

import com.example.security.cors.CorsConfig;
import com.example.security.oauth2.KeycloakRoleConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Import({
        CorsConfig.class
})
@EnableMethodSecurity
public class SecurityConfig {

    private final String audience;

    public SecurityConfig(@Value("${spring.security.oauth2.resourceserver.jwt.audiences}") String audience) {
        this.audience = audience;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r
                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(rs -> rs
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(getJwtAuthenticationConverter())
                        )
                )
                .build();
    }

    private JwtAuthenticationConverter getJwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new KeycloakRoleConverter(audience)
        );
        return jwtAuthenticationConverter;
    }

}
