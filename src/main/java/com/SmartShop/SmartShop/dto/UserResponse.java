package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.model.Client;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
@JsonPropertyOrder({ "id", "username", "email", "role", "client" })

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private ClientResponse client;
}
