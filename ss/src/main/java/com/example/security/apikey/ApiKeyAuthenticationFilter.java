package com.example.security.apikey;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
// TODO Lab idea: implement using AuthenticationFilter
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {
    // Guarantees Execution only once per request, if we register this a bean it will be added
    // to the normal FilterChain as well, not just the SecurityFilterChain

    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader("Api-Key");
        if (!StringUtils.hasText(apiKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        ApiKeyAuthenticationToken apiKeyAuthenticationToken =
                new ApiKeyAuthenticationToken(false, apiKey, null);

        Authentication authentication = authenticationManager.authenticate(apiKeyAuthenticationToken);
        if (authentication.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }
    }
}
