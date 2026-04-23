package com.app.ecom.services;

import com.app.ecom.models.*;
import com.app.ecom.repositories.CartItemRepository;
import com.app.ecom.repositories.ProductRepository;
import com.app.ecom.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty())
            return false;
        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity())
            return false;
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;
        User user = userOpt.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (productOpt.isPresent() && userOpt.isPresent()) {
            return cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get()) > 0
                    ? true : false;
        }
        return false;
    }

    public List<CartItemResponse> fetchCartItems(String userId) {
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isPresent()){
            List<CartItem> cartItemList = cartItemRepository.findAllByUserId(Long.valueOf(userId));
            return cartItemList.stream().map(this::mapToCartItemResponse)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setUser(userService.mapToUserResponse(cartItem.getUser()));
        cartItemResponse.setProduct(productService.mapToProductResponse(cartItem.getProduct()));
        cartItemResponse.setCartQuantity(cartItem.getQuantity());
        cartItemResponse.setCartPrice(cartItem.getPrice());
        return cartItemResponse;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findAllByUserId(Long.valueOf(userId));
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}
