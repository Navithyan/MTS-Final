package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.AccountDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.TransactionLog;

public interface IAccountService {
	
	// debit, credit
	List<AccountDTO> getAllAccounts(); 
	AccountDTO createAccount(AccountDTO accountDTO);
	AccountDTO getAccountById(String accountId) throws ResourceNotFoundException;
    Page<AccountDTO> getAccountsByNameUsingPage (String pname, Pageable pageable);
    Long countAccounts(); 
    public void debit(double amount, String accountId) throws ResourceNotFoundException; 
    public void credit(double amount, String accountId) throws ResourceNotFoundException; 
    public TransactionLog performTransaction(TransferRequestDTO dto); 
    List<TransactionLog> getLog(); 
}
; 