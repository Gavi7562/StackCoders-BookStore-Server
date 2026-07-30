package com.stackcoders.bookstore.constants;

/**
 * Central place for security-related constant values.
 */
public final class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String[] PUBLIC_ROUTES = {
            "/auth/register",
            "/auth/login",
            "/categories",
            "/products",
            "/products/**"
    };

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
}
