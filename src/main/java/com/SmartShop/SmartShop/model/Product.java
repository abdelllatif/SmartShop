package com.SmartShop.SmartShop.model;

import com.SmartShop.SmartShop.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double prixUnitaire;
    private int stockDisponible;

    private ProductStatus status = ProductStatus.ACTIVE;
}
