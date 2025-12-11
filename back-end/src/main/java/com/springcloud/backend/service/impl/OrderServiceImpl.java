package com.springcloud.backend.service.impl;

import com.springcloud.backend.dto.OrderRequest;
import com.springcloud.backend.mapper.OrderItemMapper;
import com.springcloud.backend.mapper.OrderMapper;
import com.springcloud.backend.mapper.ProductMapper;
import com.springcloud.backend.model.Order;
import com.springcloud.backend.model.OrderItem;
import com.springcloud.backend.model.Product;
import com.springcloud.backend.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productMapper = productMapper;
    }

    @Override
    public Order create(Long userId, OrderRequest request) {
        Order order = new Order();
        order.setUserId(userId);
        order.setMerchantId(request.getMerchantId());
        order.setStatus("CREATED");

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (OrderRequest.OrderRequestItem itemRequest : request.getItems()) {
            Product product = productMapper.findByMerchant(request.getMerchantId())
                    .stream()
                    .filter(p -> p.getId().equals(itemRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("商品不存在或不属于该商家"));
            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice());
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            items.add(item);
        }

        order.setTotalAmount(total);
        orderMapper.insert(order);

        for (OrderItem item : items) {
            item.setOrderId(order.getId());
        }
        orderItemMapper.batchInsert(items);
        order.setItems(items);
        return order;
    }

    @Override
    public List<Order> listByUser(Long userId) {
        return orderMapper.findByUser(userId);
    }

    @Override
    public List<Order> listByMerchant(Long merchantId) {
        return orderMapper.findByMerchant(merchantId);
    }
}
