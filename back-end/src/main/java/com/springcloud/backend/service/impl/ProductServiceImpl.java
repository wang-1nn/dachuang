package com.springcloud.backend.service.impl;

import com.springcloud.backend.dto.ProductRequest;
import com.springcloud.backend.mapper.ProductMapper;
import com.springcloud.backend.model.Product;
import com.springcloud.backend.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> listAll() {
        return productMapper.findAll();
    }

    @Override
    public List<Product> listByMerchant(Long merchantId) {
        return productMapper.findByMerchant(merchantId);
    }

    @Override
    public Product create(ProductRequest request, Long merchantId) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCover(request.getCover());
        product.setMerchantId(merchantId);
        productMapper.insert(product);
        return product;
    }

    @Override
    public void update(Long productId, ProductRequest request) {
        Product product = new Product();
        product.setId(productId);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCover(request.getCover());
        productMapper.update(product);
    }

    @Override
    public void delete(Long productId, Long requesterId, String requesterRole) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new IllegalArgumentException("商品不存在");
        }
        // 只有管理员或商品所属商家可以删除
        if (!"ADMIN".equalsIgnoreCase(requesterRole) && !product.getMerchantId().equals(requesterId)) {
            throw new SecurityException("没有权限删除该商品");
        }
        productMapper.delete(productId);
    }
}
