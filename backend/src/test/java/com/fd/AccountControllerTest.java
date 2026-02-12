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

import com.fd.controller.AccountController;
import com.fd.dto.AccountDTO;
import com.fd.exception.GlobalExceptionHandler;
import com.fd.security.JwtAuthFilter;
import com.fd.service.IAccountService;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(
	    controllers = AccountController.class,
	    excludeAutoConfiguration = SecurityAutoConfiguration.class,
	    excludeFilters = @ComponentScan.Filter(
	        type = FilterType.ASSIGNABLE_TYPE,
	        classes = JwtAuthFilter.class
	    )
	)
	@AutoConfigureMockMvc(addFilters = false)
	@Import(GlobalExceptionHandler.class)
	class AccountControllerTest {
	  @Autowired
	    private MockMvc mockMvc;

	    @MockitoBean
	    private IAccountService accountService;

	    @Autowired
	    private ObjectMapper objectMapper;

	    // ---------- GET ALL PRODUCTS ----------
	    @Test
	    void getAllAccounts_success() throws Exception {
	        List<AccountDTO> accounts = List.of(
	                new AccountDTO("muruga", 50000), 
	                new AccountDTO("god", 10000)
	        );

	        when(accountService.getAllAccounts()).thenReturn(accounts);

	        mockMvc.perform(get("/a-api/public/accounts"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.size()").value(2))
	                .andExpect(jsonPath("$[0].holderName").value("muruga"));
	    }
}
