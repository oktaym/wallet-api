package com.example.wallet_api.repository;


import com.example.wallet_api.model.Transaction;
import com.example.wallet_api.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByWallet(Wallet wallet);
}