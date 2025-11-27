package com.SmartShop.SmartShop.mapper;

import com.SmartShop.SmartShop.dto.*;
import com.SmartShop.SmartShop.model.Commande;
import com.SmartShop.SmartShop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommandeMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "listeArticles", target = "listeArticles")
    CommandeResponse toCommandeResponse(Commande commande);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.prixUnitaire", target = "prixUnitaire")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "quantity", target = "quantity")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}

