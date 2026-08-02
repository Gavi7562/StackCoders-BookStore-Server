package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.request.CartRequest;
import com.stackcoders.bookstore.dto.response.CartItemResponse;
import com.stackcoders.bookstore.dto.response.CartResponse;
import com.stackcoders.bookstore.entity.CartItem;
import com.stackcoders.bookstore.entity.Product;
import com.stackcoders.bookstore.entity.User;
import com.stackcoders.bookstore.repository.CartItemRepository;
import com.stackcoders.bookstore.repository.ProductRepository;
import com.stackcoders.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addToCart(String email, CartRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cartItemRepository.findByUserAndProduct(user, product).ifPresentOrElse(
                item -> {
                    item.setQuantity(item.getQuantity() + request.getQuantity());
                    cartItemRepository.save(item);
                },
                () -> {
                    CartItem cartItem = CartItem.builder()
                            .user(user)
                            .product(product)
                            .quantity(request.getQuantity())
                            .build();
                    cartItemRepository.save(cartItem);
                });
    }

    @Transactional
    public void updateCartItem(String email, Long id, CartRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        CartItem item = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (request.getQuantity() <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(request.getQuantity());
            cartItemRepository.save(item);
        }
    }

    @Transactional
    public void deleteCartItem(String email, Long id) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        CartItem item = cartItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized");
        }

        cartItemRepository.delete(item);
    }

    @Transactional(readOnly = true)
    public CartResponse getCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> items = cartItemRepository.findByUser(user);

        List<CartItemResponse> itemResponses = items.stream().map(item -> {
            String primaryImage = item.getProduct().getImages().isEmpty() ? null
                    : item.getProduct().getImages().get(0).getImageUrl();
            return CartItemResponse.builder()
                    .id(item.getId())
                    .productId(item.getProduct().getProductId())
                    .bookName(item.getProduct().getName())
                    .author(item.getProduct().getAuthor())
                    .imageUrl(primaryImage)
                    .price(item.getProduct().getPrice())
                    .quantity(item.getQuantity())
                    .subTotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .build();
        }).collect(Collectors.toList());

        Integer totalItems = items.stream().mapToInt(CartItem::getQuantity).sum();
        BigDecimal grandTotal = itemResponses.stream()
                .map(CartItemResponse::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .items(itemResponses)
                .totalItems(totalItems)
                .grandTotal(grandTotal)
                .build();
    }
}
