package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.model.PromoCode;
import com.SmartShop.SmartShop.enums.PromoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {

    Optional<PromoCode> findByCodeAndStatus(String code, PromoStatus status);

    @Modifying
    @Query("UPDATE PromoCode p SET p.status = :status WHERE p.id = :id")
    void updateStatus(Long id, PromoStatus status);
}
