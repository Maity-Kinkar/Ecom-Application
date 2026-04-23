package com.app.ecom.controllers;

import com.app.ecom.models.CartItemRequest;
import com.app.ecom.models.CartItemResponse;
import com.app.ecom.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        if (!cartService.addToCart(userId, request))
            return ResponseEntity.badRequest().body("Product out of stock or User or Product not found");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponse>> getCartDetails(@RequestHeader("X-User-ID") String userId){
        List<CartItemResponse> cartItemList = cartService.fetchCartItems(userId);
        return CollectionUtils.isEmpty(cartItemList) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(cartItemList);
    }
}
