package com.bido.profile.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record AuthContext(Long userId, String role, String email) {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CLIENT = "CLIENT";
    public static final String ROLE_SUPPLIER = "SUPPLIER";

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(role);
    }

    public boolean isClient() {
        return ROLE_CLIENT.equals(role);
    }

    public boolean isSupplier() {
        return ROLE_SUPPLIER.equals(role);
    }

    public void requireAdmin() {
        if (!isAdmin()) {
            throw forbidden();
        }
    }

    public static ResponseStatusException forbidden() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
