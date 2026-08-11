package com.stackcoders.bookstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessAnalyticsResponse {

    private BigDecimal revenue;
    private Long orders;
    private BigDecimal averageOrderValue;
    private Long transactions;

    private BigDecimal totalRevenue;
    private Long totalOrders;
    private Long totalUsers;
    private Long totalProducts;

    private String bestSellingCategory;
    private String bestSellingBook;

    private BigDecimal growthPercentage;

    // For storing trends, e.g. monthly breakdown
    private Map<String, BigDecimal> trends;
}
