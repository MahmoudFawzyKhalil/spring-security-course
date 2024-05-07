package com.example.security.apikey.AuthenticationFilter;

import com.example.security.apikey.ApiKeyAuthenticationToken;
import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import com.example.security.userdetailsservice.jpa.SecurityUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.StringUtils;

public class Converter implements AuthenticationConverter {
  //  private  final JpaUserDetailsService  userDetailsService;
    public Converter(){
    }
    @Override
    public Authentication convert(HttpServletRequest request) {
        String apiKey = request.getHeader("Api-Key");
        if (!StringUtils.hasText(apiKey)) {
            return null;
        }
       // SecurityUser securityUser = (SecurityUser) userDetailsService.loadByApiKey(apiKey);
        ApiKeyAuthenticationToken apiKeyAuthenticationToken =
                new ApiKeyAuthenticationToken(false, apiKey, null);
        return apiKeyAuthenticationToken;


    }
}
