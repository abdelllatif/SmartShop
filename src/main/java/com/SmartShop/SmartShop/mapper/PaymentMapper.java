package com.SmartShop.SmartShop.mapper;

import com.SmartShop.SmartShop.dto.PaymentRequest;
import com.SmartShop.SmartShop.dto.PaymentResponse;
import com.SmartShop.SmartShop.model.Payement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(source = "commande.id", target = "commandeId")
    PaymentResponse toPaymentResponse(Payement payement);

    @Mapping(source = "commandeId", target = "commande.id")
    Payement toPayement(PaymentRequest request);
}
