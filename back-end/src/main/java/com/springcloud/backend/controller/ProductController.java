package com.springcloud.backend.controller;

import com.springcloud.backend.dto.ProductRequest;
import com.springcloud.backend.model.Product;
import com.springcloud.backend.service.ProductService;
import com.springcloud.backend.util.RestBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public RestBean<List<Product>> listAll() {
        return RestBean.success("查询成功", productService.listAll());
    }

    @GetMapping("/merchant")
    public RestBean<List<Product>> listByMerchant(HttpServletRequest request) {
        Long merchantId = (Long) request.getAttribute("id");
        return RestBean.success("查询成功", productService.listByMerchant(merchantId));
    }

    @PostMapping
    public RestBean<Product> create(@RequestBody ProductRequest request, HttpServletRequest servletRequest) {
        Long merchantId = (Long) servletRequest.getAttribute("id");
        return RestBean.success("创建成功", productService.create(request, merchantId));
    }

    @PutMapping("/{id}")
    public RestBean<Void> update(@PathVariable("id") Long id, @RequestBody ProductRequest request) {
        productService.update(id, request);
        return RestBean.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public RestBean<Void> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        Long requesterId = (Long) request.getAttribute("id");
        String role = (String) request.getAttribute("role");
        productService.delete(id, requesterId, role);
        return RestBean.success("删除成功");
    }
}
