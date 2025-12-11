package com.springcloud.backend.controller;

import com.springcloud.backend.dto.OrderRequest;
import com.springcloud.backend.model.Order;
import com.springcloud.backend.service.OrderService;
import com.springcloud.backend.util.RestBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public RestBean<Order> create(@RequestBody OrderRequest request, HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("id");
        return RestBean.success("创建订单成功", orderService.create(userId, request));
    }

    @GetMapping
    public RestBean<List<Order>> listForCustomer(HttpServletRequest servletRequest) {
        Long userId = (Long) servletRequest.getAttribute("id");
        return RestBean.success("查询成功", orderService.listByUser(userId));
    }

    @GetMapping("/merchant")
    public RestBean<List<Order>> listForMerchant(HttpServletRequest servletRequest) {
        Long merchantId = (Long) servletRequest.getAttribute("id");
        return RestBean.success("查询成功", orderService.listByMerchant(merchantId));
    }

    @PatchMapping("/{id}/status/{status}")
    public RestBean<Void> updateStatus(@PathVariable("id") Long id, @PathVariable("status") String status, HttpServletRequest servletRequest) {
        Long actorId = (Long) servletRequest.getAttribute("id");
        String role = (String) servletRequest.getAttribute("role");
        orderService.updateStatus(id, status, actorId, role);
        return RestBean.success("订单状态已更新");
    }
}
