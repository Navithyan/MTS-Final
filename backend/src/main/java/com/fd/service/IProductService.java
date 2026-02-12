package com.fd.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fd.dto.ProductDTO;
import com.fd.exception.ResourceNotFoundException;

public interface IProductService {
	// first 
	List<ProductDTO> getProductsFromDatabase();
	ProductDTO createProduct(ProductDTO productDTO);
	ProductDTO getProductById(Integer id) throws ResourceNotFoundException;
    ProductDTO getProductByName(String pname) throws ResourceNotFoundException;
    Long countProductsByName(String pname);
    Page<ProductDTO> getProductsByNameUsingPage (String pname, Pageable pageable);
    
}
