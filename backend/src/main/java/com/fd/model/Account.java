package com.fd.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="myAccount")
public class Account {
	// Source - https://stackoverflow.com/a/79505566
	// Posted by Mehdi Rahimi, modified by community. See post 'Timeline' for change history
	// Retrieved 2026-02-11, License - CC BY-SA 4.0
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pk; 
	@Column(unique=true)
	private String accountId; // only getter
//    static int counter = 1; 
    @Column(name="holderName", nullable=false)
    private String holderName;
    @Column(name="balance", nullable=false)
    private double balance;
    @Column(name="status")
    private Boolean status; // True if active, false if not
    @Column(name="version")
    private int version; 
    @Column(name="lastUpdated")
    private LocalDateTime lastUpdated; 
    
    public Account(){
    	super();
    }
//    // parametrized constructor
//    public Account(String holderName, double balance){
//    	super(); 
//    	this.holderName = holderName; 
//    	this.balance = balance; 
////    	this.id = "ACC" + counter; 
////    	counter++; 
//    	this.status = true; 
//    	this.version = 1; 
//    	updateLastUpdated(); 
//    }
    
    
    public Account(String accountId, String holderName, double balance, Boolean status, int version,
			LocalDateTime lastUpdated) {
		super();
		this.accountId = accountId;
		this.holderName = holderName;
		this.balance = balance;
		this.status = status;
		this.version = version;
		this.lastUpdated = lastUpdated;
	}
	// all getters
    public String getAccountId(){
        return accountId; 
    }
    
    public String getHolderName(){
        return holderName; 
    }

    public double getBalance(){
        return balance; 
    }

    public Boolean getStatus(){
        return status; 
    }

    public int getVersion(){
        return version; 
    }

    public LocalDateTime getLastUpdated(){
        return lastUpdated; 
    }

    // setters 
    
    public void setHolderName(String holderName){
        this.holderName =  holderName; 
        upgradeVersion();
        updateLastUpdated();
    }

    public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void changeStatus(){
		// true if false, false if true
        this.status = !status; 
        upgradeVersion();
        updateLastUpdated();
    }

    public void updateLastUpdated(){
    	// current time
        this.lastUpdated =  LocalDateTime.now(); 
    }

    public void upgradeVersion(){
        // called during change of name and change of status
        this.version += 1; 
    }
    
    // functions

    // returns true if active and false if not. 
    public boolean isActive(){
        return status;
    }

    
 
    
}
