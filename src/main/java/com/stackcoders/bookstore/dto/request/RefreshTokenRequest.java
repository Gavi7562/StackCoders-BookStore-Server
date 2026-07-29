package com.stackcoders.bookstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Optional body for /auth/refresh. In practice the current access token is read
 * from the Authorization header, but this DTO is provided in case the frontend
 * chooses to send the token in the request body instead.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Token is required")
    private String token;
}
