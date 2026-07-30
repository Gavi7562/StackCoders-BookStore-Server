package com.stackcoders.bookstore.service.impl;

import com.stackcoders.bookstore.dto.response.CategoryResponse;
import com.stackcoders.bookstore.mapper.CategoryMapper;
import com.stackcoders.bookstore.repository.CategoryRepository;
import com.stackcoders.bookstore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
