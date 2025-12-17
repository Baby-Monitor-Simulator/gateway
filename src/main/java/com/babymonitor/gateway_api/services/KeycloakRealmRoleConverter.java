package com.babymonitor.gateway_api.services;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object realmAccessObj = jwt.getClaims().get("realm_access");

        if (!(realmAccessObj instanceof Map)) {
            return Collections.emptyList();
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> realmAccess = (Map<String, Object>) realmAccessObj;

        return RoleConverter.convertRealmRoles(realmAccess);
    }
}
