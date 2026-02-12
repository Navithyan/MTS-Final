package com.fd.service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fd.dto.ProductDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Product;
import com.fd.repository.ProductRepository;


@Service
public class ProductService implements IProductService{
	
	@Autowired
	ProductRepository productRepo; 

//	private static final Logger logger =LoggerFactory.getLogger(ProductService.class);

	@Override
	public List<ProductDTO> getProductsFromDatabase() {
//		logger.info("Fetching all the products from database.");
		// TODO Auto-generated method stub
		 return productRepo.findAll()
	                .stream()
	                .map(ProductDTO::toDTO)
	                .collect(Collectors.toList());		
	}

	@Override
	public ProductDTO createProduct(ProductDTO dto) {
		// TODO Auto-generated method stub		  
//		dto.setId(null);

        Product savedProduct = productRepo.save(ProductDTO.fromDTO(dto));

        return ProductDTO.toDTO(savedProduct);
			
	}
	
	public ProductDTO getProductById(Integer id) throws ResourceNotFoundException {
		Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + id));

        return ProductDTO.toDTO(product);
	}

	@Override
	public ProductDTO getProductByName(String pname) throws ResourceNotFoundException {
		Product product = productRepo.findByPname(pname)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Product not found with name: " + pname));

	    return ProductDTO.toDTO(product);
	}
	
	@Override
	public Long countProductsByName(String pname) {
		return productRepo.countByProductName(pname);
	}

    
    
    	
//----------------Pagination--------------------------
	@Override
   public Page<ProductDTO> getProductsByNameUsingPage (String pname, Pageable pageable) {
		return productRepo
				.findByPname(pname, pageable)
               .map(ProductDTO::toDTO);
   }
   

	

}
