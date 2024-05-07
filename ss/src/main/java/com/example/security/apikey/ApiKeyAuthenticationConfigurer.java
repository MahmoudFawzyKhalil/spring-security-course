package com.example.security.apikey;

import com.example.security.apikey.secondImpl.ApiKeyAuthenticationConverterI;
import com.example.security.apikey.secondImpl.ApiKeyAuthenticationFilterImplI;
import com.example.security.apikey.secondImpl.ApiKeyRequestMatcher;
import com.example.security.userdetailsservice.jpa.JpaUserDetailsService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class ApiKeyAuthenticationConfigurer extends AbstractHttpConfigurer<ApiKeyAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) {
        JpaUserDetailsService userDetailsService = http
                .getSharedObject(ApplicationContext.class)
                .getBean(JpaUserDetailsService.class);

        ApiKeyAuthenticationProvider apiKeyAuthenticationProvider = new ApiKeyAuthenticationProvider(userDetailsService);

        http.authenticationProvider(apiKeyAuthenticationProvider);
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

//        ApiKeyAuthenticationFilter apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilter(authenticationManager);

        /*
        this is the frist way
        here we split the filter  about the converter to be more generic
         */


//        AuthenticationConverter authenticationConverter = new ApiKeyAuthenticationConverter();
//        ApiKeyAuthenticationFilterImpl apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilterImpl(authenticationManager ,authenticationConverter);

        /*
        this is the second way
        here we split the filter  about the converter and the matcher to be more generic

         */

        String apiKeyHeader = "Api-Keyy";
        ApiKeyAuthenticationConverterI authenticationConverter = new ApiKeyAuthenticationConverterI(apiKeyHeader);
        RequestMatcher requestMatcher = new ApiKeyRequestMatcher(apiKeyHeader);
        ApiKeyAuthenticationFilterImplI apiKeyAuthenticationFilter = new  ApiKeyAuthenticationFilterImplI( requestMatcher,  authenticationConverter,  authenticationManager) ;

        http.addFilterBefore(apiKeyAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
