package com.SmartShop.SmartShop.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private Long productId;
    private int quantity;
    private double prixUnitaire;
    private double total;
}
