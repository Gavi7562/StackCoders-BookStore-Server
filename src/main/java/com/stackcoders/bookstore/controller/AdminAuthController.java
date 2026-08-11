package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.dto.request.LoginRequest;
import com.stackcoders.bookstore.dto.response.ApiResponse;
import com.stackcoders.bookstore.dto.response.AuthResponse;
import com.stackcoders.bookstore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> adminLogin(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.adminLogin(request);
        return ResponseEntity.ok(ApiResponse.success("Admin login successful", authResponse));
    }
}
