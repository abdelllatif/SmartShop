package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.ProductRequest;
import com.SmartShop.SmartShop.dto.ProductResponse;
import com.SmartShop.SmartShop.enums.ProductStatus;
import com.SmartShop.SmartShop.exception.NotFoundException;
import com.SmartShop.SmartShop.mapper.ProductMapper;
import com.SmartShop.SmartShop.model.Product;
import com.SmartShop.SmartShop.repository.ProductRepository;
import com.SmartShop.SmartShop.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toProduct(request);
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.ACTIVE);
        }
        Product saved = productRepository.save(product);
        return productMapper.toProductResponse(saved);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        existing.setName(request.getName());
        existing.setPrixUnitaire(request.getPrixUnitaire());
        existing.setStockDisponible(request.getStockDisponible());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }

        Product updated = productRepository.save(existing);
        return productMapper.toProductResponse(updated);
    }

    @Override
    public void suspendProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        product.setStatus(ProductStatus.SPANNED);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> getActiveProducts() {
        return productRepository.findByStatus(ProductStatus.ACTIVE)
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
