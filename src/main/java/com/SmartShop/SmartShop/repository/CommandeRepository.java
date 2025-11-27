package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.enums.OrderStatus;
import com.SmartShop.SmartShop.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    public List<Commande> findByClientId(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Commande c SET c.statut = :status WHERE c.id = :id")
    public Commande updateCommandeStatus(Long id, OrderStatus status);
}
