package com.api.product.infrastructure.repository;

import com.api.product.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Product entities
 */
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    
    List<ProductEntity> findByNameContainingIgnoreCase(String name);
    
    Optional<ProductEntity> findBySku(String sku);
    
    @Query("SELECT p FROM ProductEntity p WHERE p.price >= :minPrice AND p.price <= :maxPrice")
    List<ProductEntity> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);
    
    @Query("SELECT p FROM ProductEntity p WHERE p.stockQuantity > 0")
    List<ProductEntity> findInStock();
}
