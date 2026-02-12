package com.fd.model;

import java.time.LocalDateTime;
import java.util.List;

public class TransactionLog {
		// database 
	    private List<String> id; // transactionId
	    private List<String> fromAccountId; 
	    private List<String> toAccountId; 
	    private List<Double> amount; 
	    private List<Boolean> status; // success or failure (about the trnx)
	    private List<String> failureReason; 
	    private List<String> idempotencyKey;
	    private List<LocalDateTime> createdOn;
		
	    
	    public TransactionLog(List<String> fromAccountId, List<String> toAccountId, List<Double> amount,
				List<Boolean> status, List<String> failureReason, List<String> idempotencyKey) {
			super();
			this.fromAccountId = fromAccountId;
			this.toAccountId = toAccountId;
			this.amount = amount;
			this.status = status;
			this.failureReason = failureReason;
			this.idempotencyKey = idempotencyKey;
		} 
	    
	    

}
