package com.example.security.apikey.authenticationfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import java.io.IOException;

public class AuthenticationFilterImpl extends AuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationConverter authenticationConverter;
    public AuthenticationFilterImpl(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        this.authenticationConverter = authenticationConverter;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        super.doFilterInternal(request, response, filterChain);
        Authentication authentication = authenticationConverter.convert(request);
        if (authentication != null){
            filterChain.doFilter(request, response);
        }else {
            Authentication authentication1 = authenticationManager.authenticate(authentication);
            if (authentication1.isAuthenticated()) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication1);
                SecurityContextHolder.setContext(securityContext);
                filterChain.doFilter(request, response);
            }
        }

    }
}
