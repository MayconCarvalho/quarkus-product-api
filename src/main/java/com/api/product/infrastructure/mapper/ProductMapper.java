package com.api.product.infrastructure.mapper;

import com.api.product.domain.Product;
import com.api.product.infrastructure.entity.ProductEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper to convert between Product domain model and JPA entity
 */
@ApplicationScoped
public class ProductMapper {
    
    public Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setStockQuantity(entity.getStockQuantity());
        product.setCreatedAt(entity.getCreatedAt());
        product.setUpdatedAt(entity.getUpdatedAt());
        product.setSku(entity.getSku());
        
        return product;
    }
    
    public ProductEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }
        
        ProductEntity entity = new ProductEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setStockQuantity(domain.getStockQuantity());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setSku(domain.getSku());
        
        return entity;
    }
    
    public List<Product> toDomainList(List<ProductEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
    
    public List<ProductEntity> toEntityList(List<Product> domains) {
        return domains.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
