package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.PaymentStatus;
import com.SmartShop.SmartShop.enums.PaymentType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentResponse {
    private Long id;
    private Long commandeId;
    private double montant;
    private PaymentType typePaiement;
    private LocalDate datePaiement;
    private PaymentStatus statutPaiement;
}
