package com.api.product.infrastructure.repository;

import com.api.product.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByName(String name);

    Optional<Product> findBySku(String sku);

    List<Product> findByPriceRange(double minPrice, double maxPrice);

    List<Product> findInStock();

    Product save(Product product);

    void deleteById(Long id);
}
