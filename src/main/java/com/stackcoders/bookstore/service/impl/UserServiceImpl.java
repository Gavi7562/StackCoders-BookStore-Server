package com.stackcoders.bookstore.service.impl;

import com.stackcoders.bookstore.dto.response.UserResponse;
import com.stackcoders.bookstore.entity.User;
import com.stackcoders.bookstore.exception.ResourceNotFoundException;
import com.stackcoders.bookstore.mapper.UserMapper;
import com.stackcoders.bookstore.repository.UserRepository;
import com.stackcoders.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with email: " + email));

        return userMapper.toUserResponse(user);
    }

    @Override
    public java.util.List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, com.stackcoders.bookstore.dto.request.UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id: " + id));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getRole() != null) {
            try {
                user.setRole(com.stackcoders.bookstore.constants.Role.valueOf(request.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid
            }
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    public java.util.Map<String, Object> updateUsername(String email, String newUsername) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (newUsername == null || newUsername.trim().isEmpty() || newUsername.length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters");
        }

        user.setUsername(newUsername);
        userRepository.save(user);

        return buildSafeMap(user);
    }

    @Override
    public java.util.Map<String, Object> updateEmail(String email, String newEmail) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (newEmail == null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!email.equals(newEmail) && userRepository.existsByEmail(newEmail)) {
            throw new com.stackcoders.bookstore.exception.DuplicateResourceException("Email is already registered");
        }

        user.setEmail(newEmail);
        userRepository.save(user);

        return buildSafeMap(user);
    }

    @Override
    public void updatePassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private java.util.Map<String, Object> buildSafeMap(User user) {
        java.util.Map<String, Object> safeMap = new java.util.HashMap<>();
        safeMap.put("id", user.getUserId());
        safeMap.put("username", user.getUsername());
        safeMap.put("email", user.getEmail());
        safeMap.put("role", user.getRole().name());
        safeMap.put("createdAt", user.getCreatedAt());
        return safeMap;
    }
}
