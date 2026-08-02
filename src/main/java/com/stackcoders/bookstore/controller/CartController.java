package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.dto.request.CartRequest;
import com.stackcoders.bookstore.dto.response.CartResponse;
import com.stackcoders.bookstore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addToCart(Authentication authentication, @RequestBody CartRequest request) {
        cartService.addToCart(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCartItem(Authentication authentication, @PathVariable Long id,
            @RequestBody CartRequest request) {
        cartService.updateCartItem(authentication.getName(), id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(Authentication authentication, @PathVariable Long id) {
        cartService.deleteCartItem(authentication.getName(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }
}
