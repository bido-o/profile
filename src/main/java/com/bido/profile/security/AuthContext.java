package com.bido.profile.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public record AuthContext(Long userId, String role, String email) {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_CLIENT = "CLIENT";
    public static final String ROLE_SUPPLIER = "SUPPLIER";

    public void requireAdmin() {
        if (!isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public void requireAdminOrOwner(String requiredRole, Long profileId) {
        if (isAdmin()) {
            return;
        }
        if (requiredRole.equals(role) && isOwner(profileId)) {
            return;
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    private boolean isAdmin() {
        return ROLE_ADMIN.equals(role);
    }

    private boolean isOwner(Long profileId) {
        return userId != null && userId.equals(profileId);
    }
}
