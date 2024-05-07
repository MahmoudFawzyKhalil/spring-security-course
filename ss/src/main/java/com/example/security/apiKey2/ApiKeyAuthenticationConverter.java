package com.example.security.apiKey2;

import com.example.security.apikey.ApiKeyAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

public class ApiKeyAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String apiKey = request.getHeader("Api-Key");
        if (!StringUtils.hasText(apiKey)) {
            return null;
        }

        return new ApiKeyAuthenticationToken(false, apiKey, null);
    }
}