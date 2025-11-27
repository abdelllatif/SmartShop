package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.enums.PromoStatus;
import com.SmartShop.SmartShop.model.PromoCode;
import com.SmartShop.SmartShop.model.Client;

public interface PromoCodeService {
    PromoCode createPromoCode(Client client, String code);
    void expirePromoCode(PromoCode promoCode);
    PromoCode findByCodeAndStatus(String code, PromoStatus status);
}
