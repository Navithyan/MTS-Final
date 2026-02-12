package com.fd.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.model.Account;
import com.fd.service.AccountService;
import com.fd.validation.ValidProductName;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountDTO {
	private static int counter = 1; 
//	private String id; // only getter
	@Pattern(
            regexp = "^[A-Za-z ]{3,30}$",
            message = " holder name must be 3–30 characters and contain only letters"
        )
	@NotNull
	@ValidProductName
    private String holderName;
	@Min(value=1000, message="you cant create an account without even 1000 rs")
	private double balance;
//    private Boolean status; // True if active, false if not
//    private int version; 
//    private LocalDateTime lastUpdated;
    
//    static int counter = 1; 

    //constructor for use
    public AccountDTO() {}
	private static final Logger logger =LoggerFactory.getLogger(AccountService.class); 
	public AccountDTO(String holderName, double balance) {
		super();
		this.holderName = holderName; 
    	this.balance = balance; 
//    	this.id = "ACC" + counter; 
//    	counter++; 
//    	this.status = true; 
//    	this.version = 1; 
//    	updateLastUpdated();
    	System.out.println("constructor called"); 
    	logger.info("constructor called using logger"); 
    }
//	 public void updateLastUpdated(){
//	        this.lastUpdated =  LocalDateTime.now(); 
//	    }
	 
	// constructor for conversion 
	
//	public String getId() {
//		return id;
//	}
//	public AccountDTO(String id,
//			@Pattern(regexp = "^[A-Za-z ]{3,30}$", message = " holder name must be 3–30 characters and contain only letters") @NotNull String holderName,
//			@Min(value = 1000, message = "you cant create an account without even 1000 rs") double balance,
//			Boolean status, int version, LocalDateTime lastUpdated) {
//		super();
//		this.id = id;
//		this.holderName = holderName;
//		this.balance = balance;
//		this.status = status;
//		this.version = version;
//		this.lastUpdated = lastUpdated;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
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
//	public Boolean getStatus() {
//		return status;
//	}
//	public void setStatus(Boolean status) {
//		this.status = status;
//	}
//	public int getVersion() {
//		return version;
//	}
//	public void setVersion(int version) {
//		this.version = version;
//	}
//	public LocalDateTime getLastUpdated() {
//		return lastUpdated;
//	}
//	public void setLastUpdated(LocalDateTime lastUpdated) {
//		this.lastUpdated = lastUpdated;
//	} 
//	
	 /* ---------- Entity → DTO ---------- */
    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDTO(
//        		account.getId(), 
        		account.getHolderName(), 
        		account.getBalance()
//        		account.getStatus(), 
//        		account.getVersion(), 
//        		account.getLastUpdated()
        );
    }
 
    /* ---------- DTO → Entity ---------- */
    public static Account fromDTO(AccountDTO dto) {
        if (dto == null) {
            return null;
        }
        Account account = new Account(); 
        account.setAccountId("ACC" + counter++); 
        account.setHolderName(dto.getHolderName()); 
        account.setBalance(dto.getBalance()); 
        account.setStatus(true); 
        account.setVersion(1); 
        account.setLastUpdated(LocalDateTime.now()); 
        
        return account; 
  
    }
	
	
	
	

    

}
