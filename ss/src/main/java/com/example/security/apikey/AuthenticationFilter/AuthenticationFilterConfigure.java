package com.example.security.apikey.AuthenticationFilter;

import com.example.security.apikey.ApiKeyAuthenticationConfigurer;
import com.example.security.apikey.ApiKeyAuthenticationFilter;
import com.example.security.apikey.ApiKeyAuthenticationProvider;
import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class AuthenticationFilterConfigure extends AbstractHttpConfigurer<AuthenticationFilterConfigure, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        JpaUserDetailsService userDetailsService = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JpaUserDetailsService.class);

        AuthenticationFilterProvider apiKeyAuthenticationProvider = new AuthenticationFilterProvider(userDetailsService);

        http.authenticationProvider(apiKeyAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        //ApiKeyAuthenticationFilter apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilter(authenticationManager);
       AuthenticationFilterImpl apiKeyAuthenticationFilter = new AuthenticationFilterImpl(authenticationManager,new Converter());
        http.addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
