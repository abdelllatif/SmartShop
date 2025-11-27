package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.*;
import com.SmartShop.SmartShop.enums.OrderStatus;
import com.SmartShop.SmartShop.mapper.CommandeMapper;
import com.SmartShop.SmartShop.model.Client;
import com.SmartShop.SmartShop.model.Commande;
import com.SmartShop.SmartShop.model.OrderItem;
import com.SmartShop.SmartShop.model.Product;
import com.SmartShop.SmartShop.repository.ClientRepository;
import com.SmartShop.SmartShop.repository.CommandeRepository;
import com.SmartShop.SmartShop.repository.ProductRepository;
import com.SmartShop.SmartShop.service.CommandeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final CommandeMapper commandeMapper;

    public CommandeServiceImpl(CommandeRepository commandeRepository,
                               ClientRepository clientRepository,
                               ProductRepository productRepository,
                               CommandeMapper commandeMapper) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.commandeMapper = commandeMapper;
    }

    @Override
    public CommandeResponse createCommande(CommandeRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDate(LocalDate.now());
        commande.setRemise(request.getRemise());
        commande.setTva(request.getTva());
        commande.setCodePromo(request.getCodePromo());
        commande.setStatut(request.getStatut() != null ? request.getStatut() : OrderStatus.PENDING);

        List<OrderItem> items = request.getListeArticles().stream().map(itemReq -> {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrixUnitaire(product.getPrixUnitaire());
            orderItem.setTotal(product.getPrixUnitaire() * itemReq.getQuantity());
            orderItem.setCommande(commande);
            return orderItem;
        }).collect(Collectors.toList());

        double sousTotal = items.stream().mapToDouble(OrderItem::getTotal).sum();
        double total = sousTotal - commande.getRemise() + commande.getTva();
        commande.setListeArticles(items);
        commande.setSousTotal(sousTotal);
        commande.setTotal(total);
        commande.setMontantRestant(total);

        Commande saved = commandeRepository.save(commande);
        return commandeMapper.toCommandeResponse(saved);
    }

    @Override
    public CommandeResponse getCommande(Long id) {
        return commandeRepository.findById(id)
                .map(commandeMapper::toCommandeResponse)
                .orElseThrow(() -> new RuntimeException("Commande not found"));
    }

    @Override
    public List<CommandeResponse> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(commandeMapper::toCommandeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CommandeResponse updateCommande(Long id, CommandeRequest request) {
        Commande existing = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande not found"));
        existing.setStatut(request.getStatut() != null ? request.getStatut() : existing.getStatut());

        return commandeMapper.toCommandeResponse(commandeRepository.save(existing));
    }

    @Override
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
}
