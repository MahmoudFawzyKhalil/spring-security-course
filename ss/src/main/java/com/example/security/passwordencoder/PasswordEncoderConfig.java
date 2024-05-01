package com.example.security.passwordencoder;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
//        For learning Spring Security
        return NoOpPasswordEncoder.getInstance();

//        Don't use it directly, give yourself a chance for future upgrade
//        return new BCryptPasswordEncoder();

//        Use this in production
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
