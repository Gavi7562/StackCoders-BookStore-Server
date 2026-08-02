package com.stackcoders.bookstore.controller;

import com.razorpay.RazorpayException;
import com.stackcoders.bookstore.dto.request.PaymentVerifyRequest;
import com.stackcoders.bookstore.dto.response.PaymentResponse;
import com.stackcoders.bookstore.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponse> createOrder(Authentication authentication) throws RazorpayException {
        return ResponseEntity.ok(paymentService.createOrder(authentication.getName()));
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyPayment(Authentication authentication,
            @RequestBody PaymentVerifyRequest request) {
        paymentService.verifyPaymentAndCreateOrder(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }
}
