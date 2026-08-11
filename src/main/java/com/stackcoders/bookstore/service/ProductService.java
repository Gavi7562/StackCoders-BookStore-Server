package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.response.ProductResponse;
import com.stackcoders.bookstore.dto.response.SearchResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    SearchResponse searchProducts(String keyword);

    List<ProductResponse> filterProducts(String category, String author, BigDecimal minPrice, BigDecimal maxPrice,
            Boolean availability);

    List<ProductResponse> sortProducts(String sortBy);

    ProductResponse addProduct(com.stackcoders.bookstore.dto.request.ProductRequest request);

    ProductResponse updateProduct(Long id, com.stackcoders.bookstore.dto.request.ProductRequest request);

    void deleteProduct(Long id);

    com.stackcoders.bookstore.dto.response.PageResponse<ProductResponse> getAllProductsAdmin(
            String search, String category, String author, BigDecimal minPrice, BigDecimal maxPrice,
            Boolean availability, int page, int size, String sort);
}
