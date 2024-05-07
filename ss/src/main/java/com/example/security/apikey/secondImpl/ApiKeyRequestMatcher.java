package com.example.security.apikey.secondImpl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ApiKeyRequestMatcher  implements RequestMatcher {

   private String apiKey;
    public ApiKeyRequestMatcher(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        String apiKey = request.getHeader(this.apiKey);
        return apiKey!= null &&!apiKey.trim().isEmpty();
    }
}
