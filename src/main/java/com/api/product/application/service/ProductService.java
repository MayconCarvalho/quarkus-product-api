package com.api.product.application.service;

import com.api.product.domain.Product;
import com.api.product.infrastructure.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for Product business logic
 */
@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + id));
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByName(name);
    }
    
    @Transactional
    public Product createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    
    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setSku(updatedProduct.getSku());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(existingProduct);
    }
    
    @Transactional
    public void deleteProduct(Long id) {
        getProductById(id); // Check if exists
        productRepository.deleteById(id);
    }
    
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    public List<Product> getProductsInStock() {
        return productRepository.findInStock();
    }
}
