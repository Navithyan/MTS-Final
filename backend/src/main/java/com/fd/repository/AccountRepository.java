package com.fd.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fd.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
	 Page<Account> findByHolderName(String holderName, Pageable pageable);
	 @Query("SELECT COUNT(*) FROM Account a")
	 Long countAccounts();
	 Optional<Account> findByAccountId(String accountId); 
}

