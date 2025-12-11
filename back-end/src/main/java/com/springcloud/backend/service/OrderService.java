package com.springcloud.backend.service;

import com.springcloud.backend.dto.OrderRequest;
import com.springcloud.backend.model.Order;

import java.util.List;

public interface OrderService {
    Order create(Long userId, OrderRequest request);

    List<Order> listByUser(Long userId);

    List<Order> listByMerchant(Long merchantId);

    void updateStatus(Long orderId, String status, Long actorId, String actorRole);
}
