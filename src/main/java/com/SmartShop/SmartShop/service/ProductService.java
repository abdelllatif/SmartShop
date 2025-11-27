package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);

    //here I will use the method that I created in ProductRepository
    void suspendProduct(Long id);

    List<Product> getActiveProducts();
}
