package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.AccountDTO;
import com.fd.dto.TransactionDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.exception.ResourceNotFoundException;

public interface IAccountService {

    List<AccountDTO> getAllAccounts();

    AccountDTO createAccount(AccountDTO accountDTO);

    AccountDTO getAccountById(String accountId) throws ResourceNotFoundException;

    Page<AccountDTO> getAccountsByNameUsingPage(String pname, Pageable pageable);

    TransactionDTO transfer(TransferRequestDTO transferRequestDTO) throws ResourceNotFoundException;

    List<TransactionDTO> getTransactionHistory(String accountId) throws ResourceNotFoundException;
}
