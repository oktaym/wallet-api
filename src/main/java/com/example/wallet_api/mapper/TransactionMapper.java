package com.example.wallet_api.mapper;

import com.example.wallet_api.dto.TransactionDto;
import com.example.wallet_api.model.Transaction;

public class TransactionMapper {
	
	 public static TransactionDto toDto(Transaction transaction) {
	        TransactionDto dto = new TransactionDto();
	        dto.setId(transaction.getId());
	        dto.setAmount(transaction.getAmount());
	        dto.setType(transaction.getType());
	        dto.setOppositePartyType(transaction.getOppositePartyType());
	        dto.setOppositeParty(transaction.getOppositeParty());
	        dto.setStatus(transaction.getStatus());
	        dto.setWallet(WalletMapper.toDto(transaction.getWallet()));
	        return dto;
	    }

}
