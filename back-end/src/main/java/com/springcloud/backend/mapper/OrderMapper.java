package com.springcloud.backend.mapper;

import com.springcloud.backend.model.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO orders(user_id, merchant_id, status, total_amount, created_at, updated_at) VALUES(#{userId},#{merchantId},#{status},#{totalAmount},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    @Select("SELECT * FROM orders WHERE user_id=#{userId} ORDER BY created_at DESC")
    List<Order> findByUser(Long userId);

    @Select("SELECT * FROM orders WHERE merchant_id=#{merchantId} ORDER BY created_at DESC")
    List<Order> findByMerchant(Long merchantId);

    @Select("SELECT * FROM orders WHERE id=#{id}")
    Order findById(Long id);

    @Update("UPDATE orders SET status=#{status}, updated_at=NOW() WHERE id=#{id}")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
}
