package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.response.UserResponse;

public interface UserService {

    UserResponse getCurrentUser(String email);

    java.util.List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, com.stackcoders.bookstore.dto.request.UserRequest request);

    void deleteUser(Long id);

    java.util.Map<String, Object> updateUsername(String email, String newUsername);

    java.util.Map<String, Object> updateEmail(String email, String newEmail);

    void updatePassword(String email, String currentPassword, String newPassword, String confirmPassword);
}
