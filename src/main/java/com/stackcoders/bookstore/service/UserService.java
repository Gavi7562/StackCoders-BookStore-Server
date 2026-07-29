package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.response.UserResponse;

public interface UserService {

    UserResponse getCurrentUser(String email);
}
