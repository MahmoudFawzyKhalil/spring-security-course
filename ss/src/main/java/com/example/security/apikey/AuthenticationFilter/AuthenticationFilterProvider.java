package com.example.security.apikey.AuthenticationFilter;

import com.example.security.apikey.ApiKeyAuthenticationToken;
import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import com.example.security.userdetailsservice.jpa.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
@RequiredArgsConstructor
public class AuthenticationFilterProvider implements AuthenticationProvider {
    private  JpaUserDetailsService userDetailsService;

    public AuthenticationFilterProvider(JpaUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiKeyAuthenticationToken apiKeyAuth = (ApiKeyAuthenticationToken) authentication;
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadByApiKey(apiKeyAuth.getApiKey());
        if (securityUser == null) {
            throw new BadCredentialsException("Bad API Key");
        }
        return new ApiKeyAuthenticationToken(true, null, securityUser);
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return ApiKeyAuthenticationToken.class.equals(authentication);
    }
}
