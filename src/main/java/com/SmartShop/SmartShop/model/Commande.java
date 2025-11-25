package com.SmartShop.SmartShop.model;

import com.SmartShop.SmartShop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<OrderItem> listeArticles;

    private LocalDate date;

    private double sousTotal;

    private double remise;

    private double tva;

    private double total;

    private String codePromo;

    @Enumerated(EnumType.STRING)
    private OrderStatus statut;

    private double montantRestant;
}
