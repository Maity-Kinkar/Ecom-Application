package com.app.ecom.models;

import com.app.ecom.dto.ProductResponse;
import com.app.ecom.dto.UserResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    UserResponse user;
    ProductResponse product;
    Integer cartQuantity;
    BigDecimal cartPrice;
}
