package com.fd.controller;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.ProductDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Product;
import com.fd.service.IProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	IProductService service; 
	
	public ProductController(IProductService service) {
		this.service = service; 
	}
	
	@GetMapping("/secure/products")
	public List<ProductDTO> getAllProducts(){
		return service.getProductsFromDatabase();
	}
	
	
	@Transactional
	@PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {

        return ResponseEntity.ok( // 200 code (in angular) 
                service.createProduct(productDTO));
    }
	
	  @GetMapping("/secure/products/{id}")
	    public ResponseEntity<ProductDTO> getProductById(  @PathVariable int id)
	            throws ResourceNotFoundException {

	        return ResponseEntity.ok(
	                service.getProductById(id));
	    }
	  
	  @GetMapping("/products/name/{pname}")
	    public ResponseEntity<ProductDTO> getProductByName(
	            @PathVariable String pname)
	            throws ResourceNotFoundException {

	        return ResponseEntity.ok(
	                service.getProductByName(pname));
	    }
	  
	  @GetMapping("/products/count/{pname}")
	  public ResponseEntity<Long> getCountByName(
	            @PathVariable String pname)
	            throws ResourceNotFoundException {

	        return ResponseEntity.ok(
	                service.countProductsByName(pname));
	  }
	// response entity -> with headers, body 
	// it has obj (response), http code, response header , body ->
	  @GetMapping("/productpage/names/{pname}")
	    public List<ProductDTO> findProductsByNameUsingPage(
	            @PathVariable String pname,
	            Pageable pageable) {

	        return service
	                .getProductsByNameUsingPage(pname, pageable)
	                .getContent();
	    }
}
