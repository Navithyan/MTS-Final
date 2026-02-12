package com.fd;

import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.*;

import com.fd.controller.ProductController;
import com.fd.dto.ProductDTO;
import com.fd.exception.GlobalExceptionHandler;
import com.fd.security.JwtAuthFilter;
import com.fd.service.IProductService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(
	    controllers = ProductController.class,
	    excludeAutoConfiguration = SecurityAutoConfiguration.class,
	    excludeFilters = @ComponentScan.Filter(
	        type = FilterType.ASSIGNABLE_TYPE,
	        classes = JwtAuthFilter.class
	    )
	)
	@AutoConfigureMockMvc(addFilters = false)
	@Import(GlobalExceptionHandler.class)
	class ProductControllerTest {
	  @Autowired
	    private MockMvc mockMvc;

	    @MockitoBean
	    private IProductService productService;

	    @Autowired
	    private ObjectMapper objectMapper;

	    // ---------- GET ALL PRODUCTS ----------
	    @Test
	    void getAllProducts_success() throws Exception {
	        List<ProductDTO> products = List.of(
	                new ProductDTO(1, "Laptop", 50000.0),
	                new ProductDTO(2, "Mobile", 20000.0)
	        );

	        when(productService.getProductsFromDatabase()).thenReturn(products);

	        mockMvc.perform(get("/api/public/products"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.size()").value(2))
	                .andExpect(jsonPath("$[0].pname").value("Laptop"));
	    }
}
