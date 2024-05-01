package com.example.security.userdetailsservice.jpa;

import com.example.entity.Authority;
import org.springframework.security.core.GrantedAuthority;

public record SecurityAuthority(Authority delegate) implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return delegate.getName();
    }
}
