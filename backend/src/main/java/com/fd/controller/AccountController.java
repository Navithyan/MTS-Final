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
import com.fd.dto.TransactionDTO;
import com.fd.dto.TransferRequestDTO;
import com.fd.exception.ResourceNotFoundException;
import com.fd.service.IAccountService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/a-api")
public class AccountController {

    private final IAccountService acccountService;

    public AccountController(IAccountService acccountService) {
        this.acccountService = acccountService;
    }

    @GetMapping("/public/accounts")
    public List<AccountDTO> getAllAccounts() {
        return acccountService.getAllAccounts();
    }

    @Transactional
    @PostMapping("/secure/create-account")
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(acccountService.createAccount(accountDTO));
    }

    @GetMapping("/secure/accounts/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable String id)
            throws ResourceNotFoundException {

        return ResponseEntity.ok(acccountService.getAccountById(id));
    }

    @Transactional
    @PostMapping("/secure/transfer")
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(acccountService.transfer(transferRequestDTO));
    }

    @GetMapping("/secure/accounts/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactionHistory(@PathVariable String id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(acccountService.getTransactionHistory(id));
    }

    @GetMapping("/accountpage/names/{pname}")
    public List<AccountDTO> findAccountsByNameUsingPage(
            @PathVariable String pname,
            Pageable pageable) {

        return acccountService
                .getAccountsByNameUsingPage(pname, pageable)
                .getContent();
    }
}
