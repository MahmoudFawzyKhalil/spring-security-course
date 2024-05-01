package com.example.security.userdetailsservice;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

public class InMemoryUserDetailsServiceConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails bob = User.withUsername("bob@example.com")
                .authorities("course:write", "course:read", "ROLE_INSTRUCTOR")
                .password(passwordEncoder.encode("password"))
                .build();

        UserDetails alice = User.withUsername("alice@example.com")
                .authorities("course:read", "ROLE_STUDENT")
                .password(passwordEncoder.encode("password"))
                .build();

        return new InMemoryUserDetailsManager(bob, alice);
    }

}
