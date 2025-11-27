package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @Min(value = 0, message = "Unit price must be at least 0")
    private double prixUnitaire;

    @Min(value = 0, message = "Stock must be at least 0")
    private int stockDisponible;
    private ProductStatus status;
}
