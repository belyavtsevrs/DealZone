package com.epam.dealzone.domain.enums;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
    USER,ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
