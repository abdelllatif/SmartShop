package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.dto.ProductRequest;
import com.SmartShop.SmartShop.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void suspendProduct(Long id);

    List<ProductResponse> getActiveProducts();
}
