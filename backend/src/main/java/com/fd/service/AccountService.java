package com.fd.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fd.dto.AccountDTO;
import com.fd.dto.AccountDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Account;
import com.fd.model.Account;
import com.fd.repository.AccountRepository;

@Service
public class AccountService implements IAccountService {
	
	@Autowired
	AccountRepository accountRepo; 
	@Override
	public List<AccountDTO> getAllAccounts() {
	
		// TODO Auto-generated method stub
		return accountRepo.findAll()
                .stream()
                .map(AccountDTO::toDTO)
                .collect(Collectors.toList());		
	}

	@Override
	public AccountDTO createAccount(AccountDTO accountDTO) {
		// TODO Auto-generated method stub
		Account account = accountRepo.save(AccountDTO.fromDTO(accountDTO));

        return AccountDTO.toDTO(account);
	}

	@Override
	public AccountDTO getAccountById(String accountId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Account account = accountRepo.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Account not found with id: " + accountId));

        return AccountDTO.toDTO(account);
	}



	@Override
	public Page<AccountDTO> getAccountsByNameUsingPage(String holderName, Pageable pageable) {
		// TODO Auto-generated method stub
		return accountRepo
				.findByHolderName(holderName, pageable)
               .map(AccountDTO::toDTO);
	}

}
