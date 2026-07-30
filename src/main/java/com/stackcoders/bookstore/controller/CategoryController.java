package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.dto.response.ApiResponse;
import com.stackcoders.bookstore.dto.response.CategoryResponse;
import com.stackcoders.bookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success("Categories fetched successfully", categoryService.getAllCategories()));
    }
}
