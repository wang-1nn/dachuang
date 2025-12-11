package com.springcloud.backend.mapper;

import com.springcloud.backend.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM products ORDER BY created_at DESC")
    List<Product> findAll();

    @Select("SELECT * FROM products WHERE merchant_id=#{merchantId} ORDER BY created_at DESC")
    List<Product> findByMerchant(Long merchantId);

    @Insert("INSERT INTO products(name,description,price,stock,merchant_id,cover,created_at,updated_at) " +
            "VALUES(#{name},#{description},#{price},#{stock},#{merchantId},#{cover},NOW(),NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    @Update("UPDATE products SET name=#{name},description=#{description},price=#{price},stock=#{stock},cover=#{cover},updated_at=NOW() WHERE id=#{id}")
    void update(Product product);
}
