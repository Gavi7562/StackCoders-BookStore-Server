package com.stackcoders.bookstore.repository;

import com.stackcoders.bookstore.entity.CartItem;
import com.stackcoders.bookstore.entity.User;
import com.stackcoders.bookstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);

    @org.springframework.transaction.annotation.Transactional
    @org.springframework.data.jpa.repository.Modifying
    void deleteByUser(User user);

    List<CartItem> findByProduct(Product product);

    @org.springframework.transaction.annotation.Transactional
    @org.springframework.data.jpa.repository.Modifying
    void deleteByProduct(Product product);
}
