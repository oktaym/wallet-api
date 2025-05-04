package com.example.wallet_api.service;


import com.example.wallet_api.model.*;
import com.example.wallet_api.model.enums.TransactionStatus;
import com.example.wallet_api.model.enums.TransactionType;
import com.example.wallet_api.repository.TransactionRepository;
import com.example.wallet_api.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    public Transaction updateTransactionStatus(Long transactionId, TransactionStatus newStatus) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new RuntimeException("Only PENDING transactions can be updated");
        }

        Wallet wallet = transaction.getWallet();
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
