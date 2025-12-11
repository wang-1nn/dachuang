package com.springcloud.backend.service.impl;

import com.springcloud.backend.dto.AuthRequest;
import com.springcloud.backend.mapper.UserMapper;
import com.springcloud.backend.model.User;
import com.springcloud.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> login(AuthRequest request) {
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            return Optional.empty();
        }
        String encrypted = encodePassword(request.getPassword());
        if (!encrypted.equals(user.getPassword())) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public User register(AuthRequest request) {
        User existing = userMapper.findByUsername(request.getUsername());
        if (existing != null) {
            throw new IllegalStateException("用户名已存在");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodePassword(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        // 商家需要审核
        user.setStatus("MERCHANT".equalsIgnoreCase(request.getRole()) ? 2 : 0);
        userMapper.insert(user);
        return user;
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> listMerchants() {
        return userMapper.listMerchants();
    }

    @Override
    public List<User> listCustomers() {
        return userMapper.listCustomers();
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        userMapper.updateStatus(id, status);
    }

    private String encodePassword(String raw) {
        return DigestUtils.md5DigestAsHex(raw.getBytes(StandardCharsets.UTF_8));
    }
}
