package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.dto.ProductRequest;
import com.SmartShop.SmartShop.dto.ProductResponse;
import com.SmartShop.SmartShop.exception.ForbiddenException;
import com.SmartShop.SmartShop.model.Product;
import com.SmartShop.SmartShop.service.ProductService;
import com.SmartShop.SmartShop.utils.PermissionChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final HttpSession session;

    public ProductController(ProductService productService, HttpSession session) {
        this.productService = productService;
        this.session = session;
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        if (!PermissionChecker.canPerform(session, "CREATE")) {
            throw new ForbiddenException("Only ADMIN can create products");
        }
        return productService.createProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        if (!PermissionChecker.canPerform(session, "READ")) {
            throw new ForbiddenException("Access denied");
        }
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        if (!PermissionChecker.canAccessResource(session, product.getId())) {
            throw new ForbiddenException("Access denied");
        }
        return product;
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
        if (!PermissionChecker.canPerform(session, "UPDATE")) {
            throw new ForbiddenException("Only ADMIN can update products");
        }
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void suspend(@PathVariable Long id) {
        if (!PermissionChecker.canPerform(session, "DELETE")) {
            throw new ForbiddenException("Access denied");
        }
        productService.suspendProduct(id);
    }

    @GetMapping("/active")
    public List<ProductResponse> getActiveProducts() {
        return productService.getActiveProducts();
    }
}
