package com.example.security.userdetailsservice.jpa;

import com.example.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record SecurityUser(User delegate) implements UserDetails {

    public Long getId() {
        return this.delegate().getId();
    }

    @Override
    public String getUsername() {
        return this.delegate.getEmail();
    }

    @Override
    public String getPassword() {
        return this.delegate.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities()
                .stream()
                .map(SecurityAuthority::new)
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
