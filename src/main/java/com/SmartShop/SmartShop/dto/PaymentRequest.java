package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.PaymentType;
import lombok.Data;

@Data
public class PaymentRequest {
    private Long commandeId;
    private double montant;
    private PaymentType typePaiement;
}
