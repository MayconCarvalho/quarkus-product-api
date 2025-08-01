package com.api.product.application.mapper;

import com.api.product.application.dto.ProductDTO;
import com.api.product.domain.Product;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper to convert between Product domain model and DTOs
 */
@ApplicationScoped
public class ProductDtoMapper {
    
    public ProductDTO toDto(Product domain) {
        if (domain == null) {
            return null;
        }
        
        ProductDTO dto = new ProductDTO();
        dto.setId(domain.getId());
        dto.setName(domain.getName());
        dto.setDescription(domain.getDescription());
        dto.setPrice(domain.getPrice());
        dto.setStockQuantity(domain.getStockQuantity());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setUpdatedAt(domain.getUpdatedAt());
        dto.setSku(domain.getSku());
        
        return dto;
    }
    
    public Product toDomain(ProductDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Product domain = new Product();
        domain.setId(dto.getId());
        domain.setName(dto.getName());
        domain.setDescription(dto.getDescription());
        domain.setPrice(dto.getPrice());
        domain.setStockQuantity(dto.getStockQuantity());
        domain.setCreatedAt(dto.getCreatedAt());
        domain.setUpdatedAt(dto.getUpdatedAt());
        domain.setSku(dto.getSku());
        
        return domain;
    }
    
    public List<ProductDTO> toDtoList(List<Product> domains) {
        return domains.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public List<Product> toDomainList(List<ProductDTO> dtos) {
        return dtos.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
