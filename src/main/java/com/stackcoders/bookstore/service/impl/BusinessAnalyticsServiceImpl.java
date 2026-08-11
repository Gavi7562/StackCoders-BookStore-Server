package com.stackcoders.bookstore.service.impl;

import com.stackcoders.bookstore.dto.response.BusinessAnalyticsResponse;
import com.stackcoders.bookstore.repository.OrderRepository;
import com.stackcoders.bookstore.repository.ProductRepository;
import com.stackcoders.bookstore.repository.UserRepository;
import com.stackcoders.bookstore.service.BusinessAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessAnalyticsServiceImpl implements BusinessAnalyticsService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public BusinessAnalyticsResponse getDailyAnalytics(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        long orders = orderRepository.countSuccessfulOrdersBetween(start, end);
        BigDecimal revenue = orderRepository.sumRevenueBetween(start, end);
        if (revenue == null)
            revenue = BigDecimal.ZERO;

        BigDecimal avgOrder = orders > 0 ? revenue.divide(BigDecimal.valueOf(orders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return BusinessAnalyticsResponse.builder()
                .revenue(revenue)
                .orders(orders)
                .averageOrderValue(avgOrder)
                .transactions(orders)
                .build();
    }

    @Override
    public BusinessAnalyticsResponse getMonthlyAnalytics(int month, int year) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDateTime start = startOfMonth.atStartOfDay();
        LocalDateTime end = startOfMonth.plusMonths(1).atStartOfDay();

        long orders = orderRepository.countSuccessfulOrdersBetween(start, end);
        BigDecimal revenue = orderRepository.sumRevenueBetween(start, end);
        if (revenue == null)
            revenue = BigDecimal.ZERO;

        return BusinessAnalyticsResponse.builder()
                .revenue(revenue)
                .orders(orders)
                .build();
    }

    @Override
    public BusinessAnalyticsResponse getYearlyAnalytics(int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDateTime start = startOfYear.atStartOfDay();
        LocalDateTime end = startOfYear.plusYears(1).atStartOfDay();

        long orders = orderRepository.countSuccessfulOrdersBetween(start, end);
        BigDecimal revenue = orderRepository.sumRevenueBetween(start, end);
        if (revenue == null)
            revenue = BigDecimal.ZERO;

        return BusinessAnalyticsResponse.builder()
                .revenue(revenue)
                .orders(orders)
                .build();
    }

    @Override
    public BusinessAnalyticsResponse getOverallAnalytics() {
        long totalOrders = orderRepository.countAllSuccessfulOrders();
        BigDecimal totalRev = orderRepository.sumAllRevenue();
        if (totalRev == null)
            totalRev = BigDecimal.ZERO;

        long users = userRepository.count();
        long products = productRepository.count();

        BigDecimal avgOrder = totalOrders > 0
                ? totalRev.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return BusinessAnalyticsResponse.builder()
                .totalRevenue(totalRev)
                .totalOrders(totalOrders)
                .totalUsers(users)
                .totalProducts(products)
                .averageOrderValue(avgOrder)
                .build();
    }
}
