package com.example.security.apikey;

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


public class ApiKeyAuthenticationFilter2 extends AuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationConverter authenticationConverter;

    public ApiKeyAuthenticationFilter2(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = authenticationConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        ApiKeyAuthenticationToken apiKeyAuthenticationToken =
                (ApiKeyAuthenticationToken) authenticationConverter.convert(request);

        if (apiKeyAuthenticationToken == null) {
            filterChain.doFilter(request, response);
            return;
        }


        Authentication authentication = authenticationManager.authenticate(apiKeyAuthenticationToken);
        if (authentication.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }
    }


}
