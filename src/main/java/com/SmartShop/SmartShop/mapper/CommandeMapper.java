package com.SmartShop.SmartShop.mapper;

import com.SmartShop.SmartShop.dto.*;
import com.SmartShop.SmartShop.model.Commande;
import com.SmartShop.SmartShop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommandeMapper {


    @Mapping(source = "client.id", target = "clientId")
    CommandeResponse toCommandeResponse(Commande commande);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "prixUnitaire", target = "prixUnitaire")
    @Mapping(source = "total", target = "total")
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
}
