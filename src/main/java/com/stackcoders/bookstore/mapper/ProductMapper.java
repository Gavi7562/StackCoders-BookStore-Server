package com.stackcoders.bookstore.mapper;

import com.stackcoders.bookstore.dto.response.ProductResponse;
import com.stackcoders.bookstore.entity.Product;
import com.stackcoders.bookstore.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductResponse toResponse(Product product) {
        List<String> imageUrls = product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .toList();
        String imageUrl = imageUrls.stream().findFirst().orElse(null);

        return ProductResponse.builder()
                .productId(product.getProductId())
                .bookName(product.getName())
                .author(product.getAuthor())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(categoryMapper.toResponse(product.getCategory()))
                .imageUrl(imageUrl)
                .imageUrls(imageUrls)
                .stock(product.getStock())
                .createdDate(product.getCreatedAt())
                .build();
    }
}
