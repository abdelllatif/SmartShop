package com.SmartShop.SmartShop.model;

import com.SmartShop.SmartShop.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Commande commande;

    private int numeroPaiement;

    private double montant;

    private String typePaiement;

    private LocalDate datePaiement;

    private LocalDate dateEncaissement;

    @Enumerated(EnumType.STRING)
    private PaymentStatus statutPaiement;
}
