package com.stackcoders.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;
    private String bookName;
    private String author;
    private String description;
    private BigDecimal price;
    private CategoryResponse category;
    private String imageUrl;
    private List<String> imageUrls;
    private Integer stock;
    private LocalDateTime createdDate;
}
