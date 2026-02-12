package com.fd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fd.dto.AccountDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.repository.AccountRepository;
import com.fd.repository.TransactionLogRepository;

@Service
public class AccountService implements IAccountService {
	private static final Logger logger =LoggerFactory.getLogger(AccountService.class);
	@Autowired
	AccountRepository accountRepo; 
	@Autowired
	TransactionLogRepository transactionLogRepo; 
	@Override
	public List<AccountDTO> getAllAccounts() {
		// fetches all accounts from DB
		logger.info("Fetching all accounts");
		return accountRepo.findAll()
                .stream()
                .map(AccountDTO::toDTO)
                .collect(Collectors.toList());		
	}

	public List<TransactionLog> getLog() {
		// fetches all accounts from DB
		logger.info("Fetching all accounts");
		return transactionLogRepo.findAll();	
	}
	
	@Override
	public AccountDTO createAccount(AccountDTO accountDTO) {
		// creates an account in the DB
		Account account = accountRepo.save(AccountDTO.fromDTO(accountDTO));

        return AccountDTO.toDTO(account);
	}

	@Override
	public AccountDTO getAccountById(String accountId) throws ResourceNotFoundException {
		// retrive one account using account ID, starting with ACC
		Account account = accountRepo.findByAccountId(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + accountId));

        return AccountDTO.toDTO(account);
	}



	@Override
	public Page<AccountDTO> getAccountsByNameUsingPage(String holderName, Pageable pageable) {
		// applying pagination when getting account by username (same name) 
		return accountRepo
				.findByHolderName(holderName, pageable)
               .map(AccountDTO::toDTO);
	}

	@Override
	public Long countAccounts() {
		// total count of accounts retrived from account repo using custom query. 
		return accountRepo.countAccounts();
	}
	
	// functions 
	   // add any amount to existing balance
    public void credit(double amount, String accountId) throws ResourceNotFoundException{
    	Account account = accountRepo.findByAccountId(accountId)
    			.orElseThrow(() ->
                new ResourceNotFoundException(
                        "Account not found with id: " + accountId));
    	if(account != null) {	
    		double balance = account.getBalance(); 
    		balance+=amount; 
    		// returns balance 
    		account.setBalance(balance);
    		account.updateLastUpdated();
    		accountRepo.save(account); 
    	}
    } 

    // debits amount if available
    public void debit(double amount, String accountId) throws ResourceNotFoundException{
    	Account account = accountRepo.findByAccountId(accountId)
    			.orElseThrow(() ->
                new ResourceNotFoundException(
                        "Account not found with id: " + accountId));
    	if(account != null) {	
    		double balance = account.getBalance(); 
    		if (balance>=amount){
    			balance-=amount;
    			account.setBalance(balance);
    			account.updateLastUpdated();
    			accountRepo.save(account); 
    		}
    		else{
    			System.out.println("Not enough money!");
    		}
    		// returns balance 
    	}
    } 
   
    public TransactionLog performTransaction(TransferRequestDTO dto){
    	double amount = dto.getAmount(); 
	  	String fromAccountId = dto.getFromAccountId(); 
	  	String toAccountId = dto.getToAccountId(); 
	  	String failureMessage = ""; 
	  	Boolean status = true; 
	  	try {
	  		this.debit(amount, fromAccountId); 
	  		this.credit(amount, toAccountId); 
	  	}
	  	catch(Exception e){
	  		status = false; 
	  		failureMessage = e.getMessage(); 
	  	}
        TransactionLog transactionLog = new TransactionLog(
        		fromAccountId, 
        		toAccountId, 
        		amount, 
        		status,
				failureMessage);
        
        
        return transactionLogRepo.save(transactionLog);
    }

}
