package com.springcloud.backend.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role; // CUSTOMER, MERCHANT, ADMIN
    private String phone;
    private Integer status; // 0=active,1=disabled,2=pending for merchant
    private LocalDateTime createdAt;
}
