package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.PaymentRequest;
import com.SmartShop.SmartShop.dto.PaymentResponse;
import com.SmartShop.SmartShop.enums.PaymentStatus;
import com.SmartShop.SmartShop.exception.BadRequestException;
import com.SmartShop.SmartShop.exception.NotFoundException;
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

    public PaymentServiceImpl(PayementRepository payementRepository, CommandeRepository commandeRepository) {
        this.payementRepository = payementRepository;
        this.commandeRepository = commandeRepository;
    }

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        Commande commande = commandeRepository.findById(request.getCommandeId())
                .orElseThrow(() -> new NotFoundException("Commande introuvable"));

        // Calculate montant restant
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

        Payement payement = Payement.builder()
                .commande(commande)
                .montant(request.getMontant())
                .typePaiement(request.getTypePaiement().name())
                .datePaiement(LocalDate.now())
                .statutPaiement(PaymentStatus.COMPLETE)
                .build();

        payementRepository.save(payement);
        commande.setMontantRestant(montantRestant - request.getMontant());
        commandeRepository.save(commande);

        return mapToResponse(payement);
    }

    @Override
    public List<PaymentResponse> getPaymentsByCommande(Long commandeId) {
        return payementRepository.findByCommandeId(commandeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(Payement payement) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payement.getId());
        response.setCommandeId(payement.getCommande().getId());
        response.setMontant(payement.getMontant());
        response.setTypePaiement(payement.getTypePaiement().equalsIgnoreCase("CHEQUE") ? com.SmartShop.SmartShop.enums.PaymentType.CHEQUE : com.SmartShop.SmartShop.enums.PaymentType.VIREMENT);
        response.setDatePaiement(payement.getDatePaiement());
        response.setStatutPaiement(payement.getStatutPaiement());
        return response;
    }
}
