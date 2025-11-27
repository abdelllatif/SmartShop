package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.model.Payement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayementRepository extends JpaRepository<Payement, Long> {

    List<Payement> findByCommandeId(Long commandeId);

}
