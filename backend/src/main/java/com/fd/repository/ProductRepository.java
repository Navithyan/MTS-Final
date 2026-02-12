package com.fd.repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fd.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	 // this specification is enough
	 //Derived Query
	 Optional<Product> findByPname(String pname);
	 
	 
	 @Query("SELECT COUNT(p) FROM Product p WHERE p.pname = :pname")
	 Long countByProductName(String pname);
	 
	 Page<Product> findByPname(String pname, Pageable pageable);


	 
}
