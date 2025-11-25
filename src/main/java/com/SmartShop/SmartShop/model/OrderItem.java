package com.SmartShop.SmartShop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product produit;

    private int quantite;

    private double prixUnitaire;

    private double totalLigne;

    @ManyToOne
    private Commande commande;
}
