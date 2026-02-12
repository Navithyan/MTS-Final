package com.fd.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="myAccount")
public class Account {
	@Id
	private String id; // only getter
    static int counter; 
    @Column(name="holderName", nullable=false)
    private String holderName;
    @Column(name="balance", nullable=false)
    private double balance;
    @Column(name="status", nullable=false)
    private Boolean status; // True if active, false if not
    @Column(name="version")
    private int version; 
    @Column(name="lastUpdated")
    private LocalDateTime lastUpdated; 
    
    public Account(){
    	super();
    }
    // parametrized constructor
    public Account(String holderName, double balance){
    	super(); 
    	this.holderName = holderName; 
    	this.balance = balance; 
    	this.id = "ACC" + counter; 
    	counter++; 
    	this.status = true; 
    	this.version = 1; 
    	updateLastUpdated(); 
    }

    // all getters
    public String getId(){
        return id; 
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

    public void setId(String id) {
		this.id = id;
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
        this.status = !status; 
        upgradeVersion();
        updateLastUpdated();
    }

    public void updateLastUpdated(){
        this.lastUpdated =  LocalDateTime.now(); 
    }

    public void upgradeVersion(){
        // called during change of name and change of status
        this.version += 1; 
    }
    
    // functions
    public double credit(double amount){
        balance+=amount; 
        // returns balance 
        updateLastUpdated();
        return balance; 
    } 

    public double debit(double amount){
        if (balance<=amount){
            balance-=amount;
            updateLastUpdated();
        }
        else{
            System.out.println("Not enough money!");
        }
        // returns balance
        return balance; 
    }

    public boolean isActive(){
        return status;
    }

    
 
    
}
