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

import com.fd.dto.AccountDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.service.AccountService;
import com.fd.service.IAccountService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/a-api")
public class AccountController {
	
	@Autowired
	IAccountService accountService; 
	
	public AccountController(IAccountService accountService) {
		this.accountService = accountService; 
	}
	
	@GetMapping("/public/accounts")
	public List<AccountDTO> getAllAccounts(){
		return accountService.getAllAccounts();
	}
	
	@GetMapping("/log")
	public List<TransactionLog> getLog(){
		return accountService.getLog();
	}
	
	@Transactional
	@PostMapping("/create-account")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
		// creates an account and returns as a response entity
        return ResponseEntity.ok( // 200 code (in angular) 
                accountService.createAccount(accountDTO));
    }
	
	  @GetMapping("/secure/accounts/{id}")
	    public ResponseEntity<AccountDTO> getAccountById(  @PathVariable String id)
	            throws ResourceNotFoundException {
		  // returns AccountDTO (as response entity) if found -> search by id

	        return ResponseEntity.ok(
	                accountService.getAccountById(id));
	    }
	  
	
	  
	 
	// response entity -> with headers, body 
	// it has obj (response), http code, response header , body ->
	  @GetMapping("/accountpage/names/{pname}")
	    public List<AccountDTO> findAccountsByNameUsingPage(
	            @PathVariable String pname,
	            Pageable pageable) {

	        return accountService
	                .getAccountsByNameUsingPage(pname, pageable)
	                .getContent();
	    }
	  
	  @PostMapping("/transfer")
	    public ResponseEntity<TransactionLog> transfer(
	    		@RequestBody TransferRequestDTO dto ) {
		  TransactionLog log =  accountService.performTransaction(dto); 
		  	return ResponseEntity.ok(log); 
		  
	    }
}
