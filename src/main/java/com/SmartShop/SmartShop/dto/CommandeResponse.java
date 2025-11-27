package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommandeResponse {

    private Long id;
    private Long clientId;
    private List<OrderItemResponse> listeArticles;
    private LocalDate date;
    private double sousTotal;
    private double remise;
    private double tva;
    private double total;
    private String codePromo;
    private OrderStatus statut;
    private double montantRestant;
}
