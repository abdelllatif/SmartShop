package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.OrderStatus;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
public class CommandeRequest {

    @NotNull(message = "Client is required")
    private Long clientId;

    @NotEmpty(message = "At least one order item is required")
    private List<OrderItemRequest> listeArticles;

    private double remise;
    private double tva;
    private String codePromo;

    private OrderStatus statut;
}
