package com.example.security.oauth2;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record KeycloakRoleConverter(String clientName) implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String REALM_ROLE_PREFIX = "global:";
    public static final String ROLES_KEY = "roles";

    @SuppressWarnings("unchecked")
    @Override
    public Collection<GrantedAuthority> convert(final Jwt source) {
        Map<String, List<String>> realmAccess = source.getClaim("realm_access");
        List<String> realmRoles = realmAccess.get(ROLES_KEY)
                .stream()
                .map(REALM_ROLE_PREFIX::concat)
                .toList();

        Map<String, Map<String, List<String>>> resourceAccess = source.getClaim("resource_access");
        var client = resourceAccess.get(this.clientName);
        List<String> clientRoles = client.get(ROLES_KEY);

        return Stream.concat(realmRoles.stream(), clientRoles.stream())
                .map(SimpleGrantedAuthority::new)
                .map(GrantedAuthority.class::cast)
                .toList();
    }
}
