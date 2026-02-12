package com.fd.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fd.dto.AccountDTO;
import com.fd.dto.ApiResponseDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.dto.TransactionLogDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.service.IAccountService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/a-api")
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/public/accounts")
    public ResponseEntity<ApiResponseDTO<List<AccountDTO>>> getAllAccounts() {
        return ResponseEntity.ok(new ApiResponseDTO<>(accountService.getAllAccounts(), "Accounts fetched successfully"));
    }

    @GetMapping("/secure/log")
    public ResponseEntity<ApiResponseDTO<List<TransactionLogDTO>>> getLog() {
        return ResponseEntity.ok(new ApiResponseDTO<>(accountService.getLog(), "Transactions fetched successfully"));
    }

    @Transactional
    @PostMapping("/secure/create-account")
    public ResponseEntity<ApiResponseDTO<AccountDTO>> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(new ApiResponseDTO<>(accountService.createAccount(accountDTO), "Account created successfully"));
    }

    @GetMapping("/secure/accounts/{id}")
    public ResponseEntity<ApiResponseDTO<AccountDTO>> getAccountById(@PathVariable String id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(new ApiResponseDTO<>(accountService.getAccountById(id), "Account fetched successfully"));
    }

    @GetMapping("/secure/accounts/{id}/transactions")
    public ResponseEntity<ApiResponseDTO<List<TransactionLogDTO>>> getTransactionsByAccountId(@PathVariable String id) {
        return ResponseEntity.ok(
            new ApiResponseDTO<>(accountService.getTransactionsByAccountId(id), "Account transactions fetched successfully")
        );
    }

    @GetMapping("/accountpage/names/{pname}")
    public ResponseEntity<ApiResponseDTO<List<AccountDTO>>> findAccountsByNameUsingPage(
            @PathVariable String pname,
            Pageable pageable) {
        return ResponseEntity.ok(
            new ApiResponseDTO<>(accountService.getAccountsByNameUsingPage(pname, pageable).getContent(), "Accounts fetched successfully")
        );
    }

    @PostMapping("/secure/transfer")
    public ResponseEntity<ApiResponseDTO<TransactionLogDTO>> transfer(@RequestBody TransferRequestDTO dto) {
        return ResponseEntity.ok(new ApiResponseDTO<>(accountService.performTransaction(dto), "Transfer processed"));
    }
}
