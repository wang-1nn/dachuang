package com.springcloud.backend.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String phone;
    /**
     * CUSTOMER / MERCHANT / ADMIN
     */
    private String role;
}
