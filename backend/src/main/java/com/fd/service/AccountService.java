package com.fd.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fd.dto.AccountDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.dto.TransactionLogDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Account;
import com.fd.model.TransactionLog;
import com.fd.repository.AccountRepository;
import com.fd.repository.TransactionLogRepository;

@Service
public class AccountService implements IAccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    TransactionLogRepository transactionLogRepo;

    @Override
    public List<AccountDTO> getAllAccounts() {
        logger.info("Fetching all accounts");
        return accountRepo.findAll().stream().map(AccountDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionLogDTO> getLog() {
        logger.info("Fetching all transaction logs");
        return transactionLogRepo.findAll().stream().map(TransactionLogDTO::toDTO).collect(Collectors.toList());
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountDTO.fromDTO(accountDTO);
        account.setAccountId("ACC" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
        return AccountDTO.toDTO(accountRepo.save(account));
    }

    @Override
    public AccountDTO getAccountById(String accountId) throws ResourceNotFoundException {
        Account account = accountRepo.findByAccountId(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        return AccountDTO.toDTO(account);
    }

    @Override
    public Page<AccountDTO> getAccountsByNameUsingPage(String holderName, Pageable pageable) {
        return accountRepo.findByHolderName(holderName, pageable).map(AccountDTO::toDTO);
    }

    @Override
    public Long countAccounts() {
        return accountRepo.countAccounts();
    }

    @Override
    public void credit(double amount, String accountId) throws ResourceNotFoundException {
        Account account = accountRepo.findByAccountId(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        account.setBalance(account.getBalance() + amount);
        account.updateLastUpdated();
        accountRepo.save(account);
    }

    @Override
    public void debit(double amount, String accountId) throws ResourceNotFoundException {
        Account account = accountRepo.findByAccountId(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Not enough money to complete transaction");
        }

        account.setBalance(account.getBalance() - amount);
        account.updateLastUpdated();
        accountRepo.save(account);
    }

    @Override
    public TransactionLogDTO performTransaction(TransferRequestDTO dto) {
        double amount = dto.getAmount();
        String fromAccountId = dto.getFromAccountId();
        String toAccountId = dto.getToAccountId();
        String failureMessage = "";
        Boolean status = true;

        try {
            this.debit(amount, fromAccountId);
            this.credit(amount, toAccountId);
        } catch (Exception e) {
            status = false;
            failureMessage = e.getMessage();
        }

        TransactionLog transactionLog = new TransactionLog(fromAccountId, toAccountId, amount, status, failureMessage);
        return TransactionLogDTO.toDTO(transactionLogRepo.save(transactionLog));
    }

    @Override
    public List<TransactionLogDTO> getTransactionsByAccountId(String accountId) {
        return transactionLogRepo.findByFromAccountIdOrToAccountIdOrderByCreatedOnDesc(accountId, accountId)
            .stream()
            .map(TransactionLogDTO::toDTO)
            .collect(Collectors.toList());
    }
}
