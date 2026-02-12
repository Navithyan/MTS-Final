package com.fd.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.ApiResponseDTO;
import com.fd.dto.ProductDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.service.IProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @GetMapping("/secure/products")
    public ResponseEntity<ApiResponseDTO<List<ProductDTO>>> getAllProducts() {
        return ResponseEntity.ok(new ApiResponseDTO<>(service.getProductsFromDatabase(), "Products fetched successfully"));
    }

    @Transactional
    @PostMapping("/products")
    public ResponseEntity<ApiResponseDTO<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(service.createProduct(productDTO), "Product created successfully"));
    }

    @GetMapping("/secure/products/{id}")
    public ResponseEntity<ApiResponseDTO<ProductDTO>> getProductById(@PathVariable int id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponseDTO<>(service.getProductById(id), "Product fetched successfully"));
    }

    @GetMapping("/products/name/{pname}")
    public ResponseEntity<ApiResponseDTO<ProductDTO>> getProductByName(@PathVariable String pname)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponseDTO<>(service.getProductByName(pname), "Product fetched successfully"));
    }

    @GetMapping("/products/count/{pname}")
    public ResponseEntity<ApiResponseDTO<Long>> getCountByName(@PathVariable String pname)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponseDTO<>(service.countProductsByName(pname), "Product count fetched successfully"));
    }

    @GetMapping("/productpage/names/{pname}")
    public ResponseEntity<ApiResponseDTO<List<ProductDTO>>> findProductsByNameUsingPage(
            @PathVariable String pname,
            Pageable pageable) {
        return ResponseEntity.ok(
            new ApiResponseDTO<>(service.getProductsByNameUsingPage(pname, pageable).getContent(), "Products fetched successfully")
        );
    }
}
