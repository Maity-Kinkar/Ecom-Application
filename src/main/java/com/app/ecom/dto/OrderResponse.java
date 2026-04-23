package com.app.ecom.dto;

import com.app.ecom.models.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    BigDecimal totalAmount;
    OrderStatus orderStatus;
    List<OrderItemDTO> items;
    LocalDateTime createdAt;
}
