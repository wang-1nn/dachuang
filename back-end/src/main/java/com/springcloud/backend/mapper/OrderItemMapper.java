package com.springcloud.backend.mapper;

import com.springcloud.backend.model.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert({
            "<script>",
            "INSERT INTO order_items(order_id, product_id, quantity, price) VALUES",
            "<foreach collection='items' item='item' separator=','>",
            "(#{item.orderId}, #{item.productId}, #{item.quantity}, #{item.price})",
            "</foreach>",
            "</script>"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsert(List<OrderItem> items);
}
