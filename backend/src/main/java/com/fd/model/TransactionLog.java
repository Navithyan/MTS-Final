package com.fd.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TransactionLog")
public class TransactionLog {
		// database 
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long pk; 
		@Column(unique=true)
	    private String transactionId; // transactiontransactionId
	    @Column(nullable=false)
		private String fromAccountId; 
	    @Column(nullable=false)
	    private String toAccountId; 
	    @Column(nullable=false)
	    private double amount; 
	    @Column
	    private Boolean status; // success or failure (about the trnx)
	    @Column
	    private String failureReason; 
	    @Column
	    private LocalDateTime createdOn;
	    
	    public TransactionLog() {}
		public TransactionLog(String fromAccounttransactionId, String toAccounttransactionId, double amount, Boolean status,
				String failureReason) {
			super();
			this.transactionId = "TRX" + UUID.randomUUID(); 
			this.fromAccountId = fromAccounttransactionId;
			this.toAccountId = toAccounttransactionId;
			this.amount = amount;
			// call debit using from account 
			// call credit using to account 
			this.status = status;
			this.failureReason = failureReason;
			this.createdOn = LocalDateTime.now();
		}
		public Long getPk() {
			return pk;
		}
		public void setPk(Long pk) {
			this.pk = pk;
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
