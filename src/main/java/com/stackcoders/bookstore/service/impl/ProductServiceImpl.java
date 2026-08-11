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
    private final com.stackcoders.bookstore.repository.CategoryRepository categoryRepository;

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
    public List<ProductResponse> filterProducts(String category, String author, BigDecimal minPrice,
            BigDecimal maxPrice, Boolean availability) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice cannot be greater than maxPrice");
        }

        Specification<Product> specification = withFetches();

        if (category != null && !category.isBlank()) {
            specification = specification.and((root, query, cb) -> cb
                    .equal(cb.lower(root.get("category").get("categoryName")), category.trim().toLowerCase()));
        }
        if (author != null && !author.isBlank()) {
            specification = specification.and((root, query, cb) -> cb.like(cb.lower(root.get("author")),
                    "%" + author.trim().toLowerCase() + "%"));
        }
        if (minPrice != null) {
            specification = specification
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (availability != null) {
            specification = specification.and((root, query, cb) -> availability ? cb.greaterThan(root.get("stock"), 0)
                    : cb.equal(root.get("stock"), 0));
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
            default ->
                throw new IllegalArgumentException("Unsupported sort value. Use priceAsc, priceDesc, newest, or name");
        };
        return toResponses(productRepository.findAll(withFetches(), sort));
    }

    @Override
    @Transactional
    public ProductResponse addProduct(com.stackcoders.bookstore.dto.request.ProductRequest request) {
        com.stackcoders.bookstore.entity.Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getBookName())
                .author(request.getAuthor())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            java.util.List<com.stackcoders.bookstore.entity.ProductImage> images = new java.util.ArrayList<>();
            images.add(com.stackcoders.bookstore.entity.ProductImage.builder().imageUrl(request.getImageUrl())
                    .product(product).build());
            product.setImages(images);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, com.stackcoders.bookstore.dto.request.ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        com.stackcoders.bookstore.entity.Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        product.setName(request.getBookName());
        product.setAuthor(request.getAuthor());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            product.getImages().clear();
            product.getImages().add(com.stackcoders.bookstore.entity.ProductImage.builder()
                    .imageUrl(request.getImageUrl()).product(product).build());
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id: " + id));

        // Remove from all users' carts to prevent foreign key errors
        com.stackcoders.bookstore.repository.CartItemRepository cartItemRepository = org.springframework.web.context.support.WebApplicationContextUtils
                .getWebApplicationContext(
                        ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
                                .currentRequestAttributes())
                                .getRequest().getServletContext())
                .getBean(com.stackcoders.bookstore.repository.CartItemRepository.class);

        cartItemRepository.deleteByProduct(product);

        // Remove from all orders to prevent foreign key errors
        com.stackcoders.bookstore.repository.OrderItemRepository orderItemRepository = org.springframework.web.context.support.WebApplicationContextUtils
                .getWebApplicationContext(
                        ((org.springframework.web.context.request.ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder
                                .currentRequestAttributes())
                                .getRequest().getServletContext())
                .getBean(com.stackcoders.bookstore.repository.OrderItemRepository.class);

        orderItemRepository.deleteByProduct(product);

        productRepository.deleteById(id);
    }

    @Override
    public com.stackcoders.bookstore.dto.response.PageResponse<ProductResponse> getAllProductsAdmin(
            String search, String category, String author, BigDecimal minPrice, BigDecimal maxPrice,
            Boolean availability, int page, int size, String sortBy) {

        Specification<Product> specification = withFetches();

        if (search != null && !search.isBlank()) {
            final String searchKeyword = "%" + search.trim().toLowerCase() + "%";
            specification = specification.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), searchKeyword),
                    cb.like(cb.lower(root.get("author")), searchKeyword),
                    cb.like(cb.lower(root.get("description")), searchKeyword)));
        }

        if (category != null && !category.isBlank()) {
            specification = specification.and((root, query, cb) -> cb
                    .equal(cb.lower(root.get("category").get("categoryName")), category.trim().toLowerCase()));
        }

        if (author != null && !author.isBlank()) {
            specification = specification.and((root, query, cb) -> cb.like(cb.lower(root.get("author")),
                    "%" + author.trim().toLowerCase() + "%"));
        }

        if (minPrice != null) {
            specification = specification
                    .and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }
        if (availability != null) {
            specification = specification.and((root, query, cb) -> availability ? cb.greaterThan(root.get("stock"), 0)
                    : cb.equal(root.get("stock"), 0));
        }

        Sort sort = switch (sortBy == null ? "" : sortBy) {
            case "priceAsc" -> Sort.by(Sort.Direction.ASC, "price");
            case "priceDesc" -> Sort.by(Sort.Direction.DESC, "price");
            case "newest" -> Sort.by(Sort.Direction.DESC, "createdAt");
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            case "nameAsc" -> Sort.by(Sort.Direction.ASC, "name");
            case "nameDesc" -> Sort.by(Sort.Direction.DESC, "name");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };

        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest
                .of(page > 0 ? page - 1 : 0, size, sort);
        org.springframework.data.domain.Page<Product> productPage = productRepository.findAll(specification, pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(productMapper::toResponse)
                .toList();

        return com.stackcoders.bookstore.dto.response.PageResponse.<ProductResponse>builder()
                .content(content)
                .pageNumber(productPage.getNumber() + 1)
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();
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
