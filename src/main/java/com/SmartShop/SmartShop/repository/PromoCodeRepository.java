package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.model.PromoCode;
import com.SmartShop.SmartShop.enums.PromoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    Optional<PromoCode> findByCodeAndStatus(String code, PromoStatus status);
}
