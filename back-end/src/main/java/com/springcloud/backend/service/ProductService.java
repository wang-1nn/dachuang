package com.springcloud.backend.service;

import com.springcloud.backend.dto.ProductRequest;
import com.springcloud.backend.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAll();

    List<Product> listByMerchant(Long merchantId);

    Product create(ProductRequest request, Long merchantId);

    void update(Long productId, ProductRequest request);
}
