package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.PaymentRequest;
import com.SmartShop.SmartShop.dto.PaymentResponse;
import com.SmartShop.SmartShop.enums.OrderStatus;
import com.SmartShop.SmartShop.enums.PaymentStatus;
import com.SmartShop.SmartShop.exception.BadRequestException;
import com.SmartShop.SmartShop.exception.NotFoundException;
import com.SmartShop.SmartShop.mapper.PaymentMapper;
import com.SmartShop.SmartShop.model.Commande;
import com.SmartShop.SmartShop.model.Payement;
import com.SmartShop.SmartShop.repository.CommandeRepository;
import com.SmartShop.SmartShop.repository.PayementRepository;
import com.SmartShop.SmartShop.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PayementRepository payementRepository;
    private final CommandeRepository commandeRepository;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PayementRepository payementRepository,
                              CommandeRepository commandeRepository,
                              PaymentMapper paymentMapper) {
        this.payementRepository = payementRepository;
        this.commandeRepository = commandeRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));
        if (commande.getMontantRestant() <= 0) {
            throw new BadRequestException("Cette commande est déjà complètement payée.");
        }
        if (request.getMontant() <= 0) {
            throw new BadRequestException("Le montant doit être supérieur à zéro.");
        }

        Payement payement = paymentMapper.toPayement(request);
        payement.setCommande(commande);
        payement.setDatePaiement(LocalDate.now());
        payement.setStatutPaiement(PaymentStatus.EN_ATTENTE);

        Payement savedPayment = payementRepository.save(payement);
        return paymentMapper.toPaymentResponse(savedPayment);
    }



    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, PaymentStatus status) {
        Payement payement = payementRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Paiement introuvable"));

        payement.setStatutPaiement(status);
        Payement savedPayment = payementRepository.save(payement);

        if (status == PaymentStatus.COMPLETE) {
            Commande commande = payement.getCommande();

            double totalPaid = payementRepository.findByCommandeId(commande.getId())
                    .stream()
                    .filter(p -> p.getStatutPaiement() == PaymentStatus.COMPLETE)
                    .mapToDouble(Payement::getMontant)
                    .sum();

            double montantRestant = commande.getTotal() - totalPaid;
            commande.setMontantRestant(montantRestant);
            if (montantRestant <= 0) {
                commande.setStatut(OrderStatus.CONFIRMED);
            }

            commandeRepository.save(commande);
        }

        return paymentMapper.toPaymentResponse(savedPayment);
    }



    @Override
    public List<PaymentResponse> getPaymentsByCommande(Long commandeId) {
        return payementRepository.findByCommandeId(commandeId)
                .stream()
                .map(paymentMapper::toPaymentResponse)
                .collect(Collectors.toList());
    }
}
