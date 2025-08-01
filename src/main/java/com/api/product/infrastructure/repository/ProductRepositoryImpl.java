package com.api.product.infrastructure.repository;

import com.api.product.domain.Product;
import com.api.product.infrastructure.entity.ProductEntity;
import com.api.product.infrastructure.mapper.ProductMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of ProductRepository using JPA
 */
@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {
    
    @Inject
    ProductJpaRepository jpaRepository;
    
    @Inject
    ProductMapper mapper;
    
    @Override
    public List<Product> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Product> findByName(String name) {
        return mapper.toDomainList(jpaRepository.findByNameContainingIgnoreCase(name));
    }
    
    @Override
    public Optional<Product> findBySku(String sku) {
        return jpaRepository.findBySku(sku)
                .map(mapper::toDomain);
    }
    
    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return mapper.toDomainList(jpaRepository.findByPriceRange(minPrice, maxPrice));
    }
    
    @Override
    public List<Product> findInStock() {
        return mapper.toDomainList(jpaRepository.findInStock());
    }
    
    @Override
    public Product save(Product product) {
        ProductEntity entity = mapper.toEntity(product);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }
    
    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
