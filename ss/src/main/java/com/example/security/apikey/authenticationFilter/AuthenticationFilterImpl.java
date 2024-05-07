package com.example.security.apikey.authenticationFilter;

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
    AuthenticationManager authenticationManager;
    AuthenticationConverter authenticationConverter;

    public AuthenticationFilterImpl(AuthenticationManager authenticationManager,
                                    AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = authenticationConverter;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = authenticationConverter.convert(request);
        if (authentication == null) {
            filterChain.doFilter(request,response);
            return;
        }
        else{
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
