package com.SmartShop.SmartShop.mapper;

import com.SmartShop.SmartShop.dto.*;
import com.SmartShop.SmartShop.model.Commande;
import com.SmartShop.SmartShop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "listeArticles", target = "orderItems")
    CommandeResponse toCommandeResponse(Commande commande);

    @Mapping(source = "produit.id", target = "productId")
    @Mapping(source = "produit.prixUnitaire", target = "prixUnitaire")
    @Mapping(source = "total", target = "total")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
