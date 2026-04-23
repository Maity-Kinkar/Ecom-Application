package com.app.ecom.repositories;

import com.app.ecom.models.CartItem;
import com.app.ecom.models.Product;
import com.app.ecom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    Integer deleteByUserAndProduct(User user, Product product);

    List<CartItem> findAllByUserId(Long userId);

    Object deleteByUser(User user);
}
