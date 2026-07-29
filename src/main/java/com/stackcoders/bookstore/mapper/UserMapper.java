package com.stackcoders.bookstore.mapper;

import com.stackcoders.bookstore.dto.response.AuthResponse;
import com.stackcoders.bookstore.dto.response.UserResponse;
import com.stackcoders.bookstore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public AuthResponse.AuthUserSummary toAuthUserSummary(User user) {
        return AuthResponse.AuthUserSummary.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
