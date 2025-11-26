package com.SmartShop.SmartShop.dto;

import com.SmartShop.SmartShop.enums.CustomerTier;
import lombok.Data;

@Data
public class ClientResponse {
    private Long id;
    private CustomerTier niveauFidelite;
}