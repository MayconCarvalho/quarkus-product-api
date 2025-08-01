package com.api.product.presentation.controller;

import com.api.product.application.dto.ProductDTO;
import com.api.product.application.mapper.ProductDtoMapper;
import com.api.product.application.service.ProductService;
import com.api.product.domain.Product;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Product", description = "Product operations")
public class ProductController {

    @Inject
    ProductService productService;
    
    @Inject
    ProductDtoMapper mapper;
    
    @GET
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @APIResponse(
        responseCode = "200",
        description = "List of products",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class, type = SchemaType.ARRAY))
    )
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return mapper.toDtoList(products);
    }
    
    @GET
    @Path("/{id}")
    @Operation(summary = "Get product by ID", description = "Returns a product by its ID")
    @APIResponse(
        responseCode = "200",
        description = "The product",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public ProductDTO getProductById(
            @Parameter(description = "Product ID", required = true) @PathParam("id") Long id) {
        Product product = productService.getProductById(id);
        return mapper.toDto(product);
    }
    
    @POST
    @Operation(summary = "Create a new product", description = "Creates a new product")
    @APIResponse(
        responseCode = "201",
        description = "Product created",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    public Response createProduct(@Valid ProductDTO productDto) {
        Product product = mapper.toDomain(productDto);
        Product created = productService.createProduct(product);
        ProductDTO createdDto = mapper.toDto(created);
        return Response.created(URI.create("/api/products/" + created.getId()))
                .entity(createdDto)
                .build();
    }
    
    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product")
    @APIResponse(
        responseCode = "200",
        description = "Product updated",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public ProductDTO updateProduct(
            @Parameter(description = "Product ID", required = true) @PathParam("id") Long id,
            @Valid ProductDTO productDto) {
        Product product = mapper.toDomain(productDto);
        Product updated = productService.updateProduct(id, product);
        return mapper.toDto(updated);
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @APIResponse(
        responseCode = "204",
        description = "Product deleted"
    )
    @APIResponse(
        responseCode = "404",
        description = "Product not found"
    )
    public Response deleteProduct(
            @Parameter(description = "Product ID", required = true) @PathParam("id") Long id) {
        productService.deleteProduct(id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/search")
    @Operation(summary = "Search products by name", description = "Returns products matching the name")
    public List<ProductDTO> searchProducts(
            @Parameter(description = "Product name to search") @QueryParam("name") String name) {
        List<Product> products = productService.searchProductsByName(name);
        return mapper.toDtoList(products);
    }
    
    @GET
    @Path("/price-range")
    @Operation(summary = "Get products by price range", description = "Returns products within the specified price range")
    public List<ProductDTO> getProductsByPriceRange(
            @Parameter(description = "Minimum price") @QueryParam("min") double minPrice,
            @Parameter(description = "Maximum price") @QueryParam("max") double maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return mapper.toDtoList(products);
    }
    
    @GET
    @Path("/in-stock")
    @Operation(summary = "Get products in stock", description = "Returns products that are in stock")
    public List<ProductDTO> getProductsInStock() {
        List<Product> products = productService.getProductsInStock();
        return mapper.toDtoList(products);
    }
}
