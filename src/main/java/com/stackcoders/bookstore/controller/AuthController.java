package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.constants.SecurityConstants;
import com.stackcoders.bookstore.dto.request.LoginRequest;
import com.stackcoders.bookstore.dto.request.RegisterRequest;
import com.stackcoders.bookstore.dto.response.ApiResponse;
import com.stackcoders.bookstore.dto.response.AuthResponse;
import com.stackcoders.bookstore.dto.response.UserResponse;
import com.stackcoders.bookstore.service.AuthService;
import com.stackcoders.bookstore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes the authentication endpoints consumed by the frontend.
 * Endpoint paths and payload shapes are fixed by the frontend contract
 * and must not change.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout(
            @RequestHeader(SecurityConstants.AUTH_HEADER) String authHeader
    ) {
        authService.logout(authHeader);
        return ResponseEntity.ok(ApiResponse.success("Logout successful"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
            @RequestHeader(SecurityConstants.AUTH_HEADER) String authHeader
    ) {
        AuthResponse authResponse = authService.refresh(authHeader);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed successfully", authResponse));
    }

    /**
     * Returns the currently authenticated user, resolved from the JWT
     * principal set by JwtAuthenticationFilter - no ID is taken from the
     * client, preventing users from requesting someone else's profile.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        UserResponse response = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Current user fetched successfully", response));
    }
}
