package com.springcloud.backend.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Long merchantId;
    private String status; // CREATED, PAID, SHIPPED, COMPLETED
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItem> items;
}
