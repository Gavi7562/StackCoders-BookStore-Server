package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();
}
