package com.fd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fd.model.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    List<TransactionLog> findByFromAccountIdOrToAccountIdOrderByCreatedOnDesc(String fromAccountId, String toAccountId);
}
