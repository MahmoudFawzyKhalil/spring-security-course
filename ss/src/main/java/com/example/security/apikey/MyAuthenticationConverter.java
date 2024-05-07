package com.example.security.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

public class MyAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String apiKey = request.getHeader("Api-Key");
        if (!StringUtils.hasText(apiKey)) {
            return null;
        }

        ApiKeyAuthenticationToken apiKeyAuthenticationToken =
                new ApiKeyAuthenticationToken(false, apiKey, null);

        return apiKeyAuthenticationToken;
    }
}
