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

    @Override
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with email: " + email));

        return userMapper.toUserResponse(user);
    }
}
