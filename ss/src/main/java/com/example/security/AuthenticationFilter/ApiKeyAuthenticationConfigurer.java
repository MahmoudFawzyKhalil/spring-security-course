package com.example.security.AuthenticationFilter;

import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class ApiKeyAuthenticationConfigurer extends AbstractHttpConfigurer<ApiKeyAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        JpaUserDetailsService userDetailsService = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JpaUserDetailsService.class);

        com.example.security.AuthenticationFilter.ApiKeyAuthenticationProvider apiKeyAuthenticationProvider = new com.example.security.AuthenticationFilter.ApiKeyAuthenticationProvider(userDetailsService) ;
        http.authenticationProvider(apiKeyAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        AuthenticationConverter authenticationConverter = new ApiKeyAuthenticationConverter();

        com.example.security.AuthenticationFilter.ApiKeyAuthenticationFilter apiKeyAuthenticationFilter = new com.example.security.AuthenticationFilter.ApiKeyAuthenticationFilter(authenticationManager,authenticationConverter);
        http.addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
