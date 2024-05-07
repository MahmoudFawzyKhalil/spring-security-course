package com.example.security.apikey;

import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import com.example.security.userdetailsservice.jpa.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

@RequiredArgsConstructor
public class ApiKeyAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String apiKey = request.getHeader("Api-Key");

        if (apiKey == null) {
            return null;
        }

        return new ApiKeyAuthenticationToken(false, apiKey, null);
    }
}
