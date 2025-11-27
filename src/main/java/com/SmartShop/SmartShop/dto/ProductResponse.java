package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.ProductStatus;
import lombok.Data;

@Data
public class ProductResponse {

    private Long id;
    private String name;
    private double prixUnitaire;
    private int stockDisponible;
    private ProductStatus status;
}
