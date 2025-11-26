package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
