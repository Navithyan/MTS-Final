package com.fd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fd.dto.AccountDTO;
import com.fd.model.Account;
import com.fd.repository.AccountRepository;
import com.fd.service.AccountService;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest  {
	@Mock
	private AccountRepository accountRepo; 
	@InjectMocks
	private AccountService accountService; 
	 // ---------- getAccountsFromDatabase ----------
    @Test
    void getAccountsFromDatabase_success() {

    	AccountDTO a1 =  new AccountDTO("mv", 50000.0); 
        AccountDTO a2 =  new AccountDTO("god", 1000.0); 
        when(accountRepo.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<AccountDTO> result = accountService.getAllAccounts();

        assertEquals(2, result.size());
        verify(accountRepo, times(1)).findAll();
    }
}
