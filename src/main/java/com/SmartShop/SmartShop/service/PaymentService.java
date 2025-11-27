package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.dto.PaymentRequest;
import com.SmartShop.SmartShop.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    List<PaymentResponse> getPaymentsByCommande(Long commandeId);

}
