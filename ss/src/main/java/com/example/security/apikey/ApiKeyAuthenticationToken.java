package com.example.security.apikey;

import com.example.security.userdetailsservice.jpa.SecurityUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class ApiKeyAuthenticationToken implements CredentialsContainer, Authentication {

    private boolean authenticated;
    private String apiKey;
    private final SecurityUser principal;

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.principal.getAuthorities();
    }

    @Override
    public String getCredentials() {
        return apiKey;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public SecurityUser getPrincipal() {
        return this.principal;
    }

    @Override
    public String getName() {
        return this.principal == null ? null : this.principal.getUsername();
    }

    // Allows ProviderManager to erase the credentials so they don't remain in-memory
    @Override
    public void eraseCredentials() {
        this.apiKey = null;
    }
}
