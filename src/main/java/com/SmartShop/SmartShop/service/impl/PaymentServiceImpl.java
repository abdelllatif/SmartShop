package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.PaymentRequest;
import com.SmartShop.SmartShop.dto.PaymentResponse;
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

        double totalPaid = payementRepository.findByCommandeId(commande.getId())
                .stream()
                .mapToDouble(Payement::getMontant)
                .sum();

        double montantRestant = commande.getTotal() - totalPaid;

        if (montantRestant <= 0) {
            throw new BadRequestException("Cette commande est déjà complètement payée.");
        }

        if (request.getMontant() > montantRestant) {
            throw new BadRequestException("Le montant dépasse le montant restant à payer: " + montantRestant);
        }

        Payement payement = paymentMapper.toPayement(request);
        payement.setCommande(commande);
        payement.setDatePaiement(LocalDate.now());
        payement.setStatutPaiement(PaymentStatus.COMPLETE);

        Payement savedPayment = payementRepository.save(payement);
        commande.setMontantRestant(montantRestant - request.getMontant());
        commandeRepository.save(commande);

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
