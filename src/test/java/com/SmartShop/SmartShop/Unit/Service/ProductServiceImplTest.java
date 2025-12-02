package com.SmartShop.SmartShop.Unit.Service;

import com.SmartShop.SmartShop.dto.ProductRequest;
import com.SmartShop.SmartShop.dto.ProductResponse;
import com.SmartShop.SmartShop.enums.ProductStatus;
import com.SmartShop.SmartShop.exception.NotFoundException;
import com.SmartShop.SmartShop.mapper.ProductMapper;
import com.SmartShop.SmartShop.model.Product;
import com.SmartShop.SmartShop.repository.ProductRepository;
import com.SmartShop.SmartShop.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductRequest request;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // إعداد ProductRequest
        request = new ProductRequest();
        request.setName("Laptop");
        request.setPrixUnitaire(1000.0);
        request.setStockDisponible(10);
        request.setStatus(ProductStatus.ACTIVE);

        // إعداد Product
        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrixUnitaire(1000.0);
        product.setStockDisponible(10);
        product.setStatus(ProductStatus.ACTIVE);
    }

    @Test
    void createProduct_ShouldSaveAndReturnResponse() {
        when(productMapper.toProduct(request)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toProductResponse(product)).thenReturn(new ProductResponse());

        ProductResponse response = productService.createProduct(request);

        assertNotNull(response);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getAllProducts_ShouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toProductResponse(product)).thenReturn(new ProductResponse());

        List<ProductResponse> result = productService.getAllProducts();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponse(product)).thenReturn(new ProductResponse());

        ProductResponse response = productService.getProductById(1L);

        assertNotNull(response);
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_ShouldThrowNotFoundException_WhenNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnResponse() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toProductResponse(product)).thenReturn(new ProductResponse());

        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("Laptop Pro");
        updateRequest.setPrixUnitaire(1200.0);
        updateRequest.setStockDisponible(5);
        updateRequest.setStatus(ProductStatus.ACTIVE);

        ProductResponse response = productService.updateProduct(1L, updateRequest);

        assertNotNull(response);
        assertEquals("Laptop Pro", product.getName());
        assertEquals(1200.0, product.getPrixUnitaire());
        assertEquals(5, product.getStockDisponible());
        verify(productRepository).save(product);
    }

    @Test
    void suspendProduct_ShouldChangeStatus() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.suspendProduct(1L);

        assertEquals(ProductStatus.SPANNED, product.getStatus());
        verify(productRepository).save(product);
    }

    @Test
    void getActiveProducts_ShouldReturnOnlyActive() {
        when(productRepository.findByStatus(ProductStatus.ACTIVE)).thenReturn(List.of(product));
        when(productMapper.toProductResponse(product)).thenReturn(new ProductResponse());

        List<ProductResponse> activeProducts = productService.getActiveProducts();

        assertEquals(1, activeProducts.size());
        verify(productRepository).findByStatus(ProductStatus.ACTIVE);
    }
}
