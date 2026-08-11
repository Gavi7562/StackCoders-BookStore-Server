package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.dto.response.ApiResponse;
import com.stackcoders.bookstore.dto.response.UserResponse;
import com.stackcoders.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Holds user-management endpoints that are NOT part of the fixed
 * /auth/* contract already implemented in AuthController (GET /auth/me
 * lives there because the frontend calls it under /auth, per spec).
 *
 * This controller is the place to add things like:
 * GET /users/{id} (admin: view another user)
 * PUT /users/me (update own profile)
 * GET /users (admin: list users)
 * as the frontend grows beyond the current 5 routes.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Convenience alias mirroring GET /auth/me, kept here so future
     * user-management endpoints have a natural home alongside it.
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        UserResponse response = userService.getCurrentUser(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Current user fetched successfully", response));
    }

    @org.springframework.web.bind.annotation.PutMapping("/profile/username")
    public ResponseEntity<java.util.Map<String, Object>> updateUsername(
            @org.springframework.web.bind.annotation.RequestBody java.util.Map<String, String> body,
            Authentication authentication) {

        java.util.Map<String, Object> safeUser = userService.updateUsername(authentication.getName(),
                body.get("username"));

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("message", "Username updated successfully");
        response.put("data", safeUser);

        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.PutMapping("/profile/email")
    public ResponseEntity<java.util.Map<String, Object>> updateEmail(
            @org.springframework.web.bind.annotation.RequestBody java.util.Map<String, String> body,
            Authentication authentication) {

        java.util.Map<String, Object> safeUser = userService.updateEmail(authentication.getName(), body.get("email"));

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("message", "Email updated successfully");
        response.put("data", safeUser);

        return ResponseEntity.ok(response);
    }

    @org.springframework.web.bind.annotation.PutMapping("/profile/password")
    public ResponseEntity<java.util.Map<String, Object>> updatePassword(
            @org.springframework.web.bind.annotation.RequestBody java.util.Map<String, String> body,
            Authentication authentication) {

        userService.updatePassword(
                authentication.getName(),
                body.get("currentPassword"),
                body.get("newPassword"),
                body.get("confirmPassword"));

        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("success", true);
        response.put("message", "Password updated successfully");

        return ResponseEntity.ok(response);
    }
}
