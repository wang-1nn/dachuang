package com.springcloud.backend.service;

import com.springcloud.backend.dto.AuthRequest;
import com.springcloud.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(AuthRequest request);

    User register(AuthRequest request);

    User findById(Long id);

    List<User> listMerchants();

    List<User> listCustomers();

    void updateStatus(Long id, Integer status);
}
