package com.fd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fd.dto.AccountDTO;
import com.fd.dto.TransactionDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.model.Account;
import com.fd.repository.AccountRepository;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepo;
    private final ConcurrentHashMap<String, List<TransactionDTO>> transactionHistory = new ConcurrentHashMap<>();

    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepo.findAll()
                .stream()
                .map(AccountDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountDTO.fromDTO(accountDTO);

        if (account.getId() == null || account.getId().isBlank()) {
            account.setId("ACC" + System.currentTimeMillis());
        }

        if (account.getStatus() == null) {
            account.setStatus(true);
        }

        if (account.getVersion() == 0) {
            account.setVersion(1);
        }

        if (account.getLastUpdated() == null) {
            account.setLastUpdated(LocalDateTime.now());
        }

        Account saved = accountRepo.save(account);
        return AccountDTO.toDTO(saved);
    }

    @Override
    public AccountDTO getAccountById(String accountId) throws ResourceNotFoundException {
        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        return AccountDTO.toDTO(account);
    }

    @Override
    public Page<AccountDTO> getAccountsByNameUsingPage(String holderName, Pageable pageable) {
        return accountRepo
                .findByHolderName(holderName, pageable)
                .map(AccountDTO::toDTO);
    }

    @Override
    public TransactionDTO transfer(TransferRequestDTO transferRequestDTO) throws ResourceNotFoundException {
        if (transferRequestDTO.getFromAccountId().equals(transferRequestDTO.getToAccountId())) {
            throw new IllegalArgumentException("fromAccountId and toAccountId cannot be same");
        }

        Account fromAccount = accountRepo.findById(transferRequestDTO.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with id: " + transferRequestDTO.getFromAccountId()));
        Account toAccount = accountRepo.findById(transferRequestDTO.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with id: " + transferRequestDTO.getToAccountId()));

        if (fromAccount.getBalance() < transferRequestDTO.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - transferRequestDTO.getAmount());
        toAccount.setBalance(toAccount.getBalance() + transferRequestDTO.getAmount());
        fromAccount.setLastUpdated(LocalDateTime.now());
        toAccount.setLastUpdated(LocalDateTime.now());

        accountRepo.save(fromAccount);
        accountRepo.save(toAccount);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setFromAccountId(transferRequestDTO.getFromAccountId());
        transactionDTO.setToAccountId(transferRequestDTO.getToAccountId());
        transactionDTO.setAmount(transferRequestDTO.getAmount());
        transactionDTO.setStatus("SUCCESS");
        transactionDTO.setMessage("Transfer completed");
        transactionDTO.setCreatedOn(LocalDateTime.now());

        transactionHistory.computeIfAbsent(transferRequestDTO.getFromAccountId(), key -> new ArrayList<>()).add(transactionDTO);
        transactionHistory.computeIfAbsent(transferRequestDTO.getToAccountId(), key -> new ArrayList<>()).add(transactionDTO);

        return transactionDTO;
    }

    @Override
    public List<TransactionDTO> getTransactionHistory(String accountId) throws ResourceNotFoundException {
        if (!accountRepo.existsById(accountId)) {
            throw new ResourceNotFoundException("Account not found with id: " + accountId);
        }

        return transactionHistory.getOrDefault(accountId, List.of())
                .stream()
                .sorted(Comparator.comparing(TransactionDTO::getCreatedOn).reversed())
                .toList();
    }
}
