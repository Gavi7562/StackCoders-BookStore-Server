package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.response.OrderItemResponse;
import com.stackcoders.bookstore.dto.response.OrderResponse;
import com.stackcoders.bookstore.entity.Order;
import com.stackcoders.bookstore.entity.OrderItem;
import com.stackcoders.bookstore.entity.User;
import com.stackcoders.bookstore.repository.OrderItemRepository;
import com.stackcoders.bookstore.repository.OrderRepository;
import com.stackcoders.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderRepository.findByUserOrderByCreatedAtDesc(user);

        return orders.stream().map(this::mapToOrderResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(String email, String orderId) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = orderRepository.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new RuntimeException("Order not found or unauthorized"));

        return mapToOrderResponse(order);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItem> items = orderItemRepository.findByOrder(order);

        List<OrderItemResponse> itemResponses = items.stream().map(item -> {
            String primaryImage = item.getProduct().getImages().isEmpty() ? null
                    : item.getProduct().getImages().get(0).getImageUrl();
            return OrderItemResponse.builder()
                    .id(item.getId())
                    .productId(item.getProduct().getProductId())
                    .bookName(item.getProduct().getName())
                    .author(item.getProduct().getAuthor())
                    .imageUrl(primaryImage)
                    .pricePerUnit(item.getPricePerUnit())
                    .quantity(item.getQuantity())
                    .totalPrice(item.getTotalPrice())
                    .build();
        }).collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
}
