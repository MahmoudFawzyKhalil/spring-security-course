package com.example.security.apikey.secondImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class ApiKeyAuthenticationFilterImplI extends AuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RequestMatcher requestMatcher;
    private final AuthenticationConverter authenticationConverter;

    public ApiKeyAuthenticationFilterImplI( RequestMatcher requestMatcher, AuthenticationConverter authenticationConverter, AuthenticationManager authenticationManager) {
        super(authenticationManager, authenticationConverter);
        this.authenticationManager = authenticationManager;
        this.requestMatcher = requestMatcher;
        this.authenticationConverter = authenticationConverter;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            Authentication authenticationRequest = authenticationConverter.convert(request);
            try {
                Authentication authenticationResult = authenticationManager.authenticate(authenticationRequest);
                if (authenticationResult.isAuthenticated()) {
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authenticationResult);
                    SecurityContextHolder.setContext(securityContext);
                    filterChain.doFilter(request, response);
                } else {
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

}

