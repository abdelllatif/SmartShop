package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.model.Client;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private Client client;
}
