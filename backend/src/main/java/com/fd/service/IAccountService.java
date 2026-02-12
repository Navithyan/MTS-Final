package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.AccountDTO;
import com.fd.exception.ResourceNotFoundException;

public interface IAccountService {
	
	// debit, credit
	List<AccountDTO> getAllAccounts(); 
	AccountDTO createAccount(AccountDTO accountDTO);
	AccountDTO getAccountById(String accountId) throws ResourceNotFoundException;
    Page<AccountDTO> getAccountsByNameUsingPage (String pname, Pageable pageable);
}
; 