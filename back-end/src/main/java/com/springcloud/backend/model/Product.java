package com.springcloud.backend.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Long merchantId;
    private String cover;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
