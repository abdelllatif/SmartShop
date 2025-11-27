package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
