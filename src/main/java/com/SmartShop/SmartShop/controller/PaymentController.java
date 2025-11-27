package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.dto.PaymentRequest;
import com.SmartShop.SmartShop.dto.PaymentResponse;
import com.SmartShop.SmartShop.exception.ForbiddenException;
import com.SmartShop.SmartShop.service.PaymentService;
import com.SmartShop.SmartShop.utils.PermissionChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final HttpSession session;

    public PaymentController(PaymentService paymentService, HttpSession session) {
        this.paymentService = paymentService;
        this.session = session;
    }

    @PostMapping
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) {
        if (!PermissionChecker.canPerform(session, "CREATE")) {
            throw new ForbiddenException("Accès refusé");
        }
        return paymentService.createPayment(request);
    }

    @GetMapping("/commande/{commandeId}")
    public List<PaymentResponse> getPaymentsByCommande(@PathVariable Long commandeId) {
        if (!PermissionChecker.canPerform(session, "READ")) {
            throw new ForbiddenException("Accès refusé");
        }
        return paymentService.getPaymentsByCommande(commandeId);
    }
}
