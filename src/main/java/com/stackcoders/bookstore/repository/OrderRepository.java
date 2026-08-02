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
}
