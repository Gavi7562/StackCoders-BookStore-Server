package com.stackcoders.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private AuthUserSummary user;

    /**
     * Deliberately narrower than {@link UserResponse} - the login response
     * contract only exposes id, username, email, and role (no createdAt).
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AuthUserSummary {
        private Long id;
        private String username;
        private String email;
        private String role;
    }
}
