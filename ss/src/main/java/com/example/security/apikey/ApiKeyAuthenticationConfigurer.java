package com.example.security.apikey;

import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
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

        ApiKeyAuthenticationProvider apiKeyAuthenticationProvider = new ApiKeyAuthenticationProvider(userDetailsService);

        http.authenticationProvider(apiKeyAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        ApiKeyAuthenticationFilter2 apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilter2(authenticationManager, new ApiKeyAuthenticationConverter());
        http.addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
