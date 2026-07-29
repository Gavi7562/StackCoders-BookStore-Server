package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.request.LoginRequest;
import com.stackcoders.bookstore.dto.request.RegisterRequest;
import com.stackcoders.bookstore.dto.response.AuthResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    void logout(String bearerToken);

    AuthResponse refresh(String bearerToken);
}
