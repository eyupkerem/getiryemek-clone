package com.example.getiryemek_clone.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    RESTAURANT_ADMIN("RESTAURANT_ADMIN");
    private String value;

    Role(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }

    @Override
    public String getAuthority() {
        return "ROLE_"+ value;
    }




}
