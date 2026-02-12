package com.fd.dto;

import java.time.LocalDateTime;

import com.fd.model.Account;
import com.fd.validation.ValidProductName;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountDTO {
	
	private String id; // only getter
	@Pattern(
            regexp = "^[A-Za-z ]{3,30}$",
            message = " holder name must be 3–30 characters and contain only letters"
        )
	@NotNull
	@ValidProductName
    private String holderName;
	@Min(value=1000, message="you cant create an account without even 1000 rs")
	private double balance;
    private Boolean status; // True if active, false if not
    private int version; 
    private LocalDateTime lastUpdated;
    
    //constructor
	public AccountDTO(String id,
			@Pattern(regexp = "^[A-Za-z ]{3,30}$", message = " holder name must be 3–30 characters and contain only letters") @NotNull String holderName,
			@Min(value = 1000, message = "you cant create an account without even 1000 rs") double balance,
			Boolean status, int version, LocalDateTime lastUpdated) {
		super();
		this.id = id;
		this.holderName = holderName;
		this.balance = balance;
		this.status = status;
		this.version = version;
		this.lastUpdated = lastUpdated;
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
	public int getVersion() {
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
	
	 /* ---------- Entity → DTO ---------- */
    public static AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDTO(
        		account.getId(), 
        		account.getHolderName(), 
        		account.getBalance(), 
        		account.getStatus(), 
        		account.getVersion(), 
        		account.getLastUpdated()
        );
    }
 
    /* ---------- DTO → Entity ---------- */
    public static Account fromDTO(AccountDTO dto) {
        if (dto == null) {
            return null;
        }
        Account account = new Account(); 
        account.setId(dto.getId()); 
        account.setHolderName(dto.getHolderName()); 
        account.setBalance(dto.getBalance()); 
        account.setStatus(dto.getStatus()); 
        account.setVersion(dto.getVersion()); 
        account.setLastUpdated(dto.getLastUpdated()); 
        
        return account; 
  
    }
	
	
	
	

    

}
