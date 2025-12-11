package com.springcloud.backend.controller;

import com.springcloud.backend.dto.AuthRequest;
import com.springcloud.backend.model.User;
import com.springcloud.backend.service.UserService;
import com.springcloud.backend.util.JWTUtil;
import com.springcloud.backend.util.RestBean;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RestBean<Map<String, Object>> register(@RequestBody AuthRequest request) {
        User user = userService.register(request);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", JWTUtil.createToken(user));
        return RestBean.success("注册成功", result);
    }

    @PostMapping("/login")
    public RestBean<Map<String, Object>> login(@RequestBody AuthRequest request) {
        return userService.login(request)
                .map(user -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("user", user);
                    result.put("token", JWTUtil.createToken(user));
                    return RestBean.success("登录成功", result);
                })
                .orElseGet(() -> RestBean.failure(401, "用户名或密码错误"));
    }

    @GetMapping("/profile")
    public RestBean<User> profile(@RequestAttribute("id") Long id) {
        User user = userService.findById(id);
        return RestBean.success("获取成功", user);
    }
}
