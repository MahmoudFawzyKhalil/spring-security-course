package com.example.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecuritySupport {
    public static String getCurrentUserId() {
        Jwt securityUser = getCurrentUser();
        return securityUser.getSubject();
    }

    private static Jwt getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Jwt securityUser)) {
            throw new IllegalStateException("Principal must be instanceof SecurityUser");
        }
        return securityUser;
    }
}
