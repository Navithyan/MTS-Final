package com.fd.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fd.dto.AccountDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.dto.TransactionLogDTO;
import com.fd.exception.ResourceNotFoundException;

public interface IAccountService {

    List<AccountDTO> getAllAccounts();
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountById(String accountId) throws ResourceNotFoundException;
    Page<AccountDTO> getAccountsByNameUsingPage(String pname, Pageable pageable);
    Long countAccounts();
    void debit(double amount, String accountId) throws ResourceNotFoundException;
    void credit(double amount, String accountId) throws ResourceNotFoundException;
    TransactionLogDTO performTransaction(TransferRequestDTO dto);
    List<TransactionLogDTO> getLog();
    List<TransactionLogDTO> getTransactionsByAccountId(String accountId);
}
