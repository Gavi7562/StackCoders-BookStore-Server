package com.stackcoders.bookstore.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackcoders.bookstore.dto.request.PaymentVerifyRequest;
import com.stackcoders.bookstore.dto.response.PaymentResponse;
import com.stackcoders.bookstore.entity.CartItem;
import com.stackcoders.bookstore.entity.OrderItem;
import com.stackcoders.bookstore.entity.User;
import com.stackcoders.bookstore.repository.CartItemRepository;
import com.stackcoders.bookstore.repository.OrderItemRepository;
import com.stackcoders.bookstore.repository.OrderRepository;
import com.stackcoders.bookstore.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    private RazorpayClient razorpayClient;

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void init() throws RazorpayException {
        if (!"rzp_test_placeholder".equals(razorpayKeyId)) {
            this.razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        }
    }

    @Transactional
    public PaymentResponse createOrder(String email) throws RazorpayException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Razorpay Order
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", totalAmount.multiply(BigDecimal.valueOf(100)).intValue()); // amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        // For local development without valid keys, return dummy response
        if (this.razorpayClient == null) {
            return PaymentResponse.builder()
                    .orderId("order_dummy_" + System.currentTimeMillis())
                    .amount(totalAmount)
                    .currency("INR")
                    .build();
        }

        Order razorpayOrder = razorpayClient.orders.create(orderRequest);

        return PaymentResponse.builder()
                .orderId(razorpayOrder.get("id"))
                .amount(totalAmount)
                .currency("INR")
                .build();
    }

    @Transactional
    public void verifyPaymentAndCreateOrder(String email, PaymentVerifyRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (this.razorpayClient != null) {
            String generatedSignature = generateSignature(request.getRazorpayOrderId(), request.getRazorpayPaymentId());
            if (!generatedSignature.equals(request.getRazorpaySignature())) {
                throw new RuntimeException("Payment Signature verification failed");
            }
        }

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        BigDecimal totalAmount = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        com.stackcoders.bookstore.entity.Order order = com.stackcoders.bookstore.entity.Order.builder()
                .orderId(request.getRazorpayOrderId())
                .user(user)
                .totalAmount(totalAmount)
                .status("SUCCESS")
                .build();
        orderRepository.save(order);

        // Create OrderItems
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> OrderItem.builder()
                .order(order)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .pricePerUnit(cartItem.getProduct().getPrice())
                .totalPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .build()).collect(Collectors.toList());
        orderItemRepository.saveAll(orderItems);

        // Clear Cart
        cartItemRepository.deleteByUser(user);
    }

    private String generateSignature(String orderId, String paymentId) {
        try {
            String payload = orderId + "|" + paymentId;
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(razorpayKeySecret.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate signature", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
