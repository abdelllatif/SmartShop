package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.CommandeRequest;
import com.SmartShop.SmartShop.dto.CommandeResponse;
import com.SmartShop.SmartShop.enums.OrderStatus;
import com.SmartShop.SmartShop.enums.PromoStatus;
import com.SmartShop.SmartShop.mapper.CommandeMapper;
import com.SmartShop.SmartShop.model.Client;
import com.SmartShop.SmartShop.model.Commande;
import com.SmartShop.SmartShop.model.OrderItem;
import com.SmartShop.SmartShop.model.Product;
import com.SmartShop.SmartShop.model.PromoCode;
import com.SmartShop.SmartShop.repository.ClientRepository;
import com.SmartShop.SmartShop.repository.CommandeRepository;
import com.SmartShop.SmartShop.repository.ProductRepository;
import com.SmartShop.SmartShop.service.CommandeService;
import com.SmartShop.SmartShop.service.PromoCodeService;
import com.SmartShop.SmartShop.utils.PromoCodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final CommandeMapper commandeMapper;
    private final PromoCodeService promoCodeService;

    public CommandeServiceImpl(CommandeRepository commandeRepository,
                               ClientRepository clientRepository,
                               ProductRepository productRepository,
                               CommandeMapper commandeMapper,
                               PromoCodeService promoCodeService) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.commandeMapper = commandeMapper;
        this.promoCodeService = promoCodeService;
    }

    @Override
    @Transactional
    public CommandeResponse createCommande(CommandeRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDate(LocalDate.now());
        commande.setTva(request.getTva());
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

        if (request.getCodePromo() != null && !request.getCodePromo().isEmpty()) {
            PromoCode promoCode = promoCodeService.findByCodeAndStatus(request.getCodePromo(), PromoStatus.ACTIVE);
            if (promoCode == null) throw new RuntimeException("Promo code not found or expired");


            promoCodeService.expirePromoCode(promoCode);
            commande.setCodePromo(promoCode.getCode());
            commande.setRemise(sousTotal * 0.05);
        } else {
            commande.setRemise(0);
            commande.setCodePromo(null);
        }

        double total = sousTotal - commande.getRemise() + commande.getTva();
        commande.setListeArticles(items);
        commande.setSousTotal(sousTotal);
        commande.setTotal(total);
        commande.setMontantRestant(total);
        promoCodeService.createPromoCode(client, "PROMO-" + PromoCodeGenerator.generatePromoCode(8));

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
    @Transactional
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


    @Override
    public List<CommandeResponse> getCommandesByClientId(Long id){
        return commandeRepository.findByClientId(id).stream().map(commandeMapper::toCommandeResponse).collect(Collectors.toList());
    }
}
