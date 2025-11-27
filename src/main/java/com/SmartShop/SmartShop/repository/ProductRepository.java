package com.SmartShop.SmartShop.repository;

import com.SmartShop.SmartShop.enums.ProductStatus;
import com.SmartShop.SmartShop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(ProductStatus status);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
    Product updateProductStatus(@Param("id") Long id, ProductStatus status);
}
