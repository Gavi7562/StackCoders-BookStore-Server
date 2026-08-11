package com.stackcoders.bookstore.repository;

import com.stackcoders.bookstore.entity.OrderItem;
import com.stackcoders.bookstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);

    @org.springframework.transaction.annotation.Transactional
    @org.springframework.data.jpa.repository.Modifying
    void deleteByProduct(com.stackcoders.bookstore.entity.Product product);
}
