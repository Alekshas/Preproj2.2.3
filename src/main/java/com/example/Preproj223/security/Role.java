package com.example.Preproj223.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
