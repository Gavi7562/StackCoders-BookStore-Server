package com.stackcoders.bookstore.mapper;

import com.stackcoders.bookstore.dto.response.CategoryResponse;
import com.stackcoders.bookstore.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
