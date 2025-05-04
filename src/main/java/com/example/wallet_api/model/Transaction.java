package com.example.wallet_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import com.example.wallet_api.model.enums.OppositePartyType;
import com.example.wallet_api.model.enums.TransactionStatus;
import com.example.wallet_api.model.enums.TransactionType;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Wallet wallet;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // DEPOSIT, WITHDRAW

    @Enumerated(EnumType.STRING)
    private OppositePartyType oppositePartyType; // IBAN, PAYMENT

    private String oppositeParty;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING, APPROVED, DENIED
    
    

	public Transaction() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
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
