package com.stackcoders.bookstore.repository;

import com.stackcoders.bookstore.entity.Order;
import com.stackcoders.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserOrderByCreatedAtDesc(User user);

    Optional<Order> findByOrderIdAndUser(String orderId, User user);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'SUCCESS' AND o.createdAt >= :startDate AND o.createdAt < :endDate")
    long countSuccessfulOrdersBetween(
            @org.springframework.data.repository.query.Param("startDate") java.time.LocalDateTime startDate,
            @org.springframework.data.repository.query.Param("endDate") java.time.LocalDateTime endDate);

    @org.springframework.data.jpa.repository.Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'SUCCESS' AND o.createdAt >= :startDate AND o.createdAt < :endDate")
    java.math.BigDecimal sumRevenueBetween(
            @org.springframework.data.repository.query.Param("startDate") java.time.LocalDateTime startDate,
            @org.springframework.data.repository.query.Param("endDate") java.time.LocalDateTime endDate);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'SUCCESS'")
    long countAllSuccessfulOrders();

    @org.springframework.data.jpa.repository.Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'SUCCESS'")
    java.math.BigDecimal sumAllRevenue();
}
