package com.example.security;

import com.example.security.apikey.ApiKeyAuthenticationConfigurer;
import com.example.security.passwordencoder.PasswordEncoderConfig;
import com.example.security.rolehierarchy.RoleHierarchyConfig;
import com.example.security.userdetailsservice.jpa.JpaUserDetailsServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
@Import({
//        InMemoryUserDetailsServiceConfig.class,
        PasswordEncoderConfig.class,
        RoleHierarchyConfig.class,
        JpaUserDetailsServiceConfig.class,
//        CorsConfig.class
})
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        configureBasicAuthentication(http);
        configureEndpointSecurity(http);
        configureCsrf(http);
        configureSessionManagement(http);
        configureApiKeyAuthentication(http);
        return http.build();
    }

    private static void configureBasicAuthentication(HttpSecurity http) throws Exception {
//        http.httpBasic(b -> b
//                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.I_AM_A_TEAPOT))
//        );
        http.httpBasic(Customizer.withDefaults());
    }

    private static void configureEndpointSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(r -> r
//                        .anyRequest().hasAuthority("read")
//                        .anyRequest().hasAnyAuthority("read", "write")
//                        .anyRequest().hasRole("ADMIN")
//                        .anyRequest().hasAnyRole("ADMIN", "MANAGER")
//                        .anyRequest().access(new WebExpressionAuthorizationManager("isAuthenticated()"))  // SpEL  --> authorization rules
//                        .requestMatchers("/courses").hasAuthority("courses:read")
                        .anyRequest().authenticated()
        );
    }

    private static void configureCsrf(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
    }

    private static void configureSessionManagement(HttpSecurity http) throws Exception {
        http.sessionManagement(s -> s
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
    }

    private static void configureApiKeyAuthentication(HttpSecurity http) throws Exception {
        http.with(new ApiKeyAuthenticationConfigurer(), Customizer.withDefaults());
    }

}
