package com.springcloud.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long merchantId;
    private List<OrderRequestItem> items;

    @Data
    public static class OrderRequestItem {
        private Long productId;
        private Integer quantity;
    }
}
