package com.example.security.apikey.AuthenticationFilter;

import com.example.security.apikey.ApiKeyAuthenticationToken;
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
import org.springframework.util.StringUtils;

import java.io.IOException;

public class AuthenticationFilterImpl extends AuthenticationFilter {
    private  AuthenticationManager authenticationManager;
    private  AuthenticationConverter converter;
    public AuthenticationFilterImpl(AuthenticationManager authenticationManager, AuthenticationConverter converter) {
        super(authenticationManager, converter);
        this.authenticationManager = authenticationManager;
        this.converter = converter;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        String apiKey = request.getHeader("Api-Key");
//        if (!StringUtils.hasText(apiKey)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        ApiKeyAuthenticationToken apiKeyAuthenticationToken =
//                new ApiKeyAuthenticationToken(false, apiKey, null);

        Authentication authentication = converter.convert(request);
        if(authentication==null){
            filterChain.doFilter(request, response);
            return;
        }
       Authentication authentication2 = authenticationManager.authenticate(authentication);

       // Converter converter = new Converter()
      //  Authentication authentication = new Converter();
        if (authentication2.isAuthenticated()) {
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication2);
            SecurityContextHolder.setContext(securityContext);
            filterChain.doFilter(request, response);
        }
    }
}
