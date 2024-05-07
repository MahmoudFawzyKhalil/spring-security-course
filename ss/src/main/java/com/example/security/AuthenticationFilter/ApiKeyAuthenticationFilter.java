package com.example.security.AuthenticationFilter;

import com.example.security.apikey.ApiKeyAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// TODO Lab idea: implement using AuthenticationFilter
public class ApiKeyAuthenticationFilter extends AuthenticationFilter {
    // Guarantees Execution only once per request, if we register this a bean it will be added
    // to the normal FilterChain as well, not just the SecurityFilterChain

    private final AuthenticationManager authenticationManager;

    private final AuthenticationConverter authenticationConverter;
    public ApiKeyAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        this.authenticationConverter = authenticationConverter;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {



        Authentication authenticationCheck = authenticationConverter.convert(request);
        if(authenticationCheck == null){
            filterChain.doFilter(request,response);
            return ;
        }
        Authentication authentication = authenticationManager.authenticate(authenticationCheck);
     if (authentication.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }
    }
}
