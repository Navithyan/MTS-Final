package com.fd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.fd.model.Account;
import com.fd.model.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog,Long>{

}
