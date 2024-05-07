package com.example.security.apikey;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import java.io.IOException;

public class ApiAuthenticationFilterHunter extends AuthenticationFilter {
    private final AuthenticationConverter hunterConverter;
    private final AuthenticationManager authenticationManager;

    public ApiAuthenticationFilterHunter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        this.hunterConverter = authenticationConverter;
        this.authenticationManager = authenticationManager;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = this.hunterConverter.convert(request);
        if (auth == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication hun = this.authenticationManager.authenticate(auth);
        if (hun.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(hun);
            SecurityContextHolder.setContext(securityContext);
        }
        filterChain.doFilter(request, response);


    }



}
