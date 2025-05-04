package com.example.wallet_api.dto;

import java.math.BigDecimal;

import com.example.wallet_api.model.enums.OppositePartyType;

public class DepositRequest {
	private BigDecimal amount;
    private OppositePartyType sourceType;
    private String source;
    
    
	public DepositRequest() {
		super();
	}
	public DepositRequest(BigDecimal amount, OppositePartyType sourceType, String source) {
		super();
		this.amount = amount;
		this.sourceType = sourceType;
		this.source = source;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public OppositePartyType getSourceType() {
		return sourceType;
	}
	public void setSourceType(OppositePartyType sourceType) {
		this.sourceType = sourceType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
    
    

}
