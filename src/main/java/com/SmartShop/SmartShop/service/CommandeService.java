package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.dto.CommandeRequest;
import com.SmartShop.SmartShop.dto.CommandeResponse;
import com.SmartShop.SmartShop.enums.OrderStatus;
import com.SmartShop.SmartShop.model.Commande;

import java.util.List;

public interface CommandeService {

    CommandeResponse createCommande(CommandeRequest request);

    CommandeResponse getCommande(Long id);

    List<CommandeResponse> getAllCommandes();

    CommandeResponse updateCommande(Long id, CommandeRequest request);

    void deleteCommande(Long id);

    List<CommandeResponse> getAllCommandesByClientId(Long id);

    CommandeResponse updateCommandeStatus(Long id, OrderStatus status);
}
