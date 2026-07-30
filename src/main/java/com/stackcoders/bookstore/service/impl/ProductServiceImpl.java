package com.stackcoders.bookstore.service.impl;

import com.stackcoders.bookstore.dto.response.ProductResponse;
import com.stackcoders.bookstore.dto.response.SearchResponse;
import com.stackcoders.bookstore.entity.Product;
import com.stackcoders.bookstore.exception.ResourceNotFoundException;
import com.stackcoders.bookstore.mapper.ProductMapper;
import com.stackcoders.bookstore.repository.ProductRepository;
import com.stackcoders.bookstore.service.ProductService;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        return toResponses(productRepository.findAll());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.getCategory().getCategoryName();
        product.getImages().size();
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return toResponses(productRepository.findByCategory_CategoryId(categoryId));
    }

    @Override
    public SearchResponse searchProducts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Search keyword is required");
        }
        List<ProductResponse> products = toResponses(productRepository.search(keyword.trim()));
        return SearchResponse.builder()
                .keyword(keyword.trim())
                .totalResults(products.size())
                .products(products)
                .build();
    }

    @Override
    public List<ProductResponse> filterProducts(String category, String author, BigDecimal minPrice, BigDecimal maxPrice, Boolean availability) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }

        Specification<Product> specification = withFetches();

        if (category != null && !category.isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("category").get("categoryName")), category.trim().toLowerCase()));
        }
        if (author != null && !author.isBlank()) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("author")), "%" + author.trim().toLowerCase() + "%"));
        }
        if (minPrice != null) {
            specification = specification.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (availability != null) {
            specification = specification.and((root, query, cb) ->
                    availability ? cb.greaterThan(root.get("stock"), 0) : cb.equal(root.get("stock"), 0));
        }

        return toResponses(productRepository.findAll(specification));
    }

    @Override
    public List<ProductResponse> sortProducts(String sortBy) {
        Sort sort = switch (sortBy == null ? "" : sortBy) {
            case "priceAsc" -> Sort.by(Sort.Direction.ASC, "price");
            case "priceDesc" -> Sort.by(Sort.Direction.DESC, "price");
            case "newest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "name" -> Sort.by(Sort.Direction.ASC, "name");
            default -> throw new IllegalArgumentException("Unsupported sort value. Use priceAsc, priceDesc, newest, or name");
        };
        return toResponses(productRepository.findAll(withFetches(), sort));
    }

    private Specification<Product> withFetches() {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class) {
                root.fetch("category", JoinType.INNER);
                root.fetch("images", JoinType.LEFT);
                query.distinct(true);
            }
            return cb.conjunction();
        };
    }

    private List<ProductResponse> toResponses(List<Product> products) {
        return products.stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
