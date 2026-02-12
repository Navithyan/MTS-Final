package com.fd.dto;

import java.time.LocalDateTime;

import com.fd.model.TransactionLog;

public class TransactionLogDTO {

    private String transactionId;
    private String fromAccountId;
    private String toAccountId;
    private double amount;
    private Boolean status;
    private String failureReason;
    private LocalDateTime createdOn;

    public TransactionLogDTO() {
    }

    public TransactionLogDTO(String transactionId, String fromAccountId, String toAccountId, double amount, Boolean status,
            String failureReason, LocalDateTime createdOn) {
        this.transactionId = transactionId;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.status = status;
        this.failureReason = failureReason;
        this.createdOn = createdOn;
    }

    public static TransactionLogDTO toDTO(TransactionLog transactionLog) {
        return new TransactionLogDTO(
            transactionLog.getTransactionId(),
            transactionLog.getFromAccountId(),
            transactionLog.getToAccountId(),
            transactionLog.getAmount(),
            transactionLog.getStatus(),
            transactionLog.getFailureReason(),
            transactionLog.getCreatedOn()
        );
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
