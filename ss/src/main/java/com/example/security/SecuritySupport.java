package com.example.security;

import com.example.security.userdetailsservice.jpa.SecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class SecuritySupport {
    public static Long getCurrentUserId() {
        SecurityUser securityUser = getCurrentUser();
        return securityUser.getId();
    }

    private static SecurityUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof SecurityUser securityUser)) {
            throw new IllegalStateException("Principal must be instanceof SecurityUser");
        }
        return securityUser;
    }

    public static Set<String> getCurrentUserAuthorities() {
        return getCurrentUser().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
