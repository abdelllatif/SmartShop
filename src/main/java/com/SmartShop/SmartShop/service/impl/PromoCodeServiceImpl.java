package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.model.PromoCode;
import com.SmartShop.SmartShop.model.Client;
import com.SmartShop.SmartShop.enums.PromoStatus;
import com.SmartShop.SmartShop.repository.PromoCodeRepository;
import com.SmartShop.SmartShop.service.PromoCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeServiceImpl(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }

    @Override
    public PromoCode createPromoCode(Client client, String code) {
        PromoCode promoCode = PromoCode.builder()
                .client(client)
                .code(code)
                .status(PromoStatus.ACTIVE)
                .used(false)
                .build();
        return promoCodeRepository.save(promoCode);
    }

    @Override
    @Transactional
    public void expirePromoCode(PromoCode promoCode) {
        promoCodeRepository.updateStatus(promoCode.getId(), PromoStatus.EXPIRE);
    }

}
