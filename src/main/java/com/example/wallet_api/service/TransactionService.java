package com.example.wallet_api.service;


import com.example.wallet_api.dto.WalletDto;
import com.example.wallet_api.model.*;
import com.example.wallet_api.model.enums.TransactionStatus;
import com.example.wallet_api.model.enums.TransactionType;
import com.example.wallet_api.repository.TransactionRepository;
import com.example.wallet_api.repository.UserRepository;
import com.example.wallet_api.repository.WalletRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    
    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository,UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public Transaction updateTransactionStatus(Long transactionId, TransactionStatus newStatus,Principal principal) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        
        User user = userRepository.findByUsername(principal.getName())
	            .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = transaction.getWallet();
        
	    if (!"EMPLOYEE".equals(user.getRole().toString()) &&
	            !wallet.getCustomer().getId().equals(user.getId())) {
	            throw new RuntimeException("Unauthorized access to this wallet");
	        }

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Only PENDING transactions can be updated");
        }

    
        BigDecimal amount = transaction.getAmount();

        if (transaction.getType() == TransactionType.DEPOSIT && newStatus == TransactionStatus.APPROVED) {
            wallet.setUsableBalance(wallet.getUsableBalance().add(amount));
        }
        
        if (transaction.getType() == TransactionType.DEPOSIT && newStatus == TransactionStatus.DENIED) {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        }

        if (transaction.getType() == TransactionType.WITHDRAW) {
            if (newStatus == TransactionStatus.APPROVED) {
                wallet.setBalance(wallet.getBalance().subtract(amount));
            } else if (newStatus == TransactionStatus.DENIED) {
                wallet.setUsableBalance(wallet.getUsableBalance().add(amount));
            }
        }

        transaction.setStatus(newStatus);
        walletRepository.save(wallet);
        return transactionRepository.save(transaction);
    }
}
