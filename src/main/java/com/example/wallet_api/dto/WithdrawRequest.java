package com.example.wallet_api.dto;

import java.math.BigDecimal;

import com.example.wallet_api.model.enums.OppositePartyType;

public class WithdrawRequest {
	 	private BigDecimal amount;
	    private String destination;
	    private OppositePartyType destType;
	    
	    
		public WithdrawRequest() {
			super();
		}
		public WithdrawRequest(BigDecimal amount, String destination, OppositePartyType destType) {
			super();
			this.amount = amount;
			this.destination = destination;
			this.destType = destType;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public OppositePartyType getDestType() {
			return destType;
		}
		public void setDestType(OppositePartyType destType) {
			this.destType = destType;
		}
	    
}
