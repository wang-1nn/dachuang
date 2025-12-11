package com.springcloud.backend.controller;

import com.springcloud.backend.model.User;
import com.springcloud.backend.service.UserService;
import com.springcloud.backend.util.RestBean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public RestBean<Map<String, List<User>>> listUsers() {
        Map<String, List<User>> result = new HashMap<>();
        result.put("merchants", userService.listMerchants());
        result.put("customers", userService.listCustomers());
        return RestBean.success("查询成功", result);
    }

    @PatchMapping("/users/{id}/status/{status}")
    public RestBean<Void> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
        userService.updateStatus(id, status);
        return RestBean.success("状态已更新");
    }
}
