package com.example.wallet_api.dto;

import java.math.BigDecimal;

import com.example.wallet_api.model.enums.OppositePartyType;
import com.example.wallet_api.model.enums.TransactionStatus;
import com.example.wallet_api.model.enums.TransactionType;

public class TransactionDto {
	 	private Long id;
	    private WalletDto wallet;
	    private BigDecimal amount;
	    private TransactionType type;
	    private OppositePartyType oppositePartyType;
	    private String oppositeParty;
	    private TransactionStatus status;
	    
	    
		public TransactionDto() {
			super();
		}
		public TransactionDto(Long id, WalletDto wallet, BigDecimal amount, TransactionType type,
				OppositePartyType oppositePartyType, String oppositeParty, TransactionStatus status) {
			super();
			this.id = id;
			this.wallet = wallet;
			this.amount = amount;
			this.type = type;
			this.oppositePartyType = oppositePartyType;
			this.oppositeParty = oppositeParty;
			this.status = status;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public WalletDto getWallet() {
			return wallet;
		}
		public void setWallet(WalletDto wallet) {
			this.wallet = wallet;
		}
		public BigDecimal getAmount() {
			return amount;
		}
		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
		public TransactionType getType() {
			return type;
		}
		public void setType(TransactionType type) {
			this.type = type;
		}
		public OppositePartyType getOppositePartyType() {
			return oppositePartyType;
		}
		public void setOppositePartyType(OppositePartyType oppositePartyType) {
			this.oppositePartyType = oppositePartyType;
		}
		public String getOppositeParty() {
			return oppositeParty;
		}
		public void setOppositeParty(String oppositeParty) {
			this.oppositeParty = oppositeParty;
		}
		public TransactionStatus getStatus() {
			return status;
		}
		public void setStatus(TransactionStatus status) {
			this.status = status;
		}
	    
	    
}
