package com.example.security.apikey.secondImpl;

import com.example.security.apikey.ApiKeyAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

public class ApiKeyAuthenticationConverterI implements AuthenticationConverter {

    private  String apiKey;

    public ApiKeyAuthenticationConverterI(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        String apiKey = request.getHeader(this.apiKey);
        return new ApiKeyAuthenticationToken(false, apiKey, null);
    }
}
