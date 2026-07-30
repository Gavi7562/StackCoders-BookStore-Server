package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.dto.response.ApiResponse;
import com.stackcoders.bookstore.dto.response.ProductResponse;
import com.stackcoders.bookstore.dto.response.SearchResponse;
import com.stackcoders.bookstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        return ResponseEntity.ok(ApiResponse.success("Products fetched successfully", productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Product fetched successfully", productService.getProductById(id)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success("Products fetched successfully", productService.getProductsByCategory(categoryId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SearchResponse>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", productService.searchProducts(keyword)));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean availability
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                "Products filtered successfully",
                productService.filterProducts(category, author, minPrice, maxPrice, availability)
        ));
    }

    @GetMapping("/sort")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> sortProducts(@RequestParam String sortBy) {
        return ResponseEntity.ok(ApiResponse.success("Products sorted successfully", productService.sortProducts(sortBy)));
    }
}
