package com.fd.dto;

import java.time.LocalDateTime;

import com.fd.model.Account;
import com.fd.validation.ValidProductName;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountDTO {

    private String id;

    @Pattern(
        regexp = "^[A-Za-z ]{3,30}$",
        message = " holder name must be 3–30 characters and contain only letters"
    )
    @NotNull
    @ValidProductName
    private String holderName;

    @Min(value = 1000, message = "you cant create an account without even 1000 rs")
    private double balance;

    private Boolean status;
    private Integer version;
    private LocalDateTime lastUpdated;

    public AccountDTO() {
    }

    public AccountDTO(String id, String holderName, double balance, Boolean status, int version, LocalDateTime lastUpdated) {
        this.id = id;
        this.holderName = holderName;
        this.balance = balance;
        this.status = status;
        this.version = version;
        this.lastUpdated = lastUpdated;
    }

    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountDTO(
            account.getAccountId(),
            account.getHolderName(),
            account.getBalance(),
            account.getStatus(),
            account.getVersion(),
            account.getLastUpdated()
        );
    }

    public static Account fromDTO(AccountDTO dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setHolderName(dto.getHolderName());
        account.setBalance(dto.getBalance());
        account.setStatus(dto.getStatus() == null ? true : dto.getStatus());
        account.setVersion(dto.getVersion() > 0 ? dto.getVersion() : 1);
        account.updateLastUpdated();
        return account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
