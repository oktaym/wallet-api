package com.example.wallet_api.service;


import com.example.wallet_api.dto.TransactionDto;
import com.example.wallet_api.dto.UserDto;
import com.example.wallet_api.dto.WalletDto;
import com.example.wallet_api.mapper.TransactionMapper;
import com.example.wallet_api.mapper.WalletMapper;
import com.example.wallet_api.model.*;
import com.example.wallet_api.model.enums.Currency;
import com.example.wallet_api.model.enums.OppositePartyType;
import com.example.wallet_api.model.enums.TransactionStatus;
import com.example.wallet_api.model.enums.TransactionType;
import com.example.wallet_api.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository,
                         UserRepository userRepository,
                         TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public WalletDto createWallet(String walletName, Currency currency,
                                boolean forShopping, boolean forWithdraw,
                                String username) {

        User customer = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setWalletName(walletName);
        wallet.setCurrency(currency);
        wallet.setActiveForShopping(forShopping);
        wallet.setActiveForWithdraw(forWithdraw);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setUsableBalance(BigDecimal.ZERO);
        wallet.setCustomer(customer);
        Wallet savedWallet = walletRepository.save(wallet);
        return WalletMapper.toDto(savedWallet);
    }
    
    public WalletDto getWalletById(Long walletId, Principal principal) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!wallet.getCustomer().getId().equals(user.getId())
                && !"EMPLOYEE".equals(user.getRole().toString())) {
            throw new RuntimeException("Unauthorized access to wallet");
        }

        return WalletMapper.toDto(wallet);
    }

    public List<WalletDto> listWallets(Principal  principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        System.out.println("user role "+user.getRole());

        List<Wallet> wallets = "EMPLOYEE".equals(user.getRole().name()) ?
                walletRepository.findAll() :
                walletRepository.findAllByCustomer(user);

        return wallets.stream()
                .map(WalletMapper::toDto)
                .collect(Collectors.toList());
    }

    public TransactionDto  deposit(Long walletId, BigDecimal amount,
                                OppositePartyType sourceType, String source) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setOppositePartyType(sourceType);
        transaction.setOppositeParty(source);

        if (amount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            transaction.setStatus(TransactionStatus.PENDING);
            wallet.setBalance(wallet.getBalance().add(amount));
        } else {
            transaction.setStatus(TransactionStatus.APPROVED);
            wallet.setBalance(wallet.getBalance().add(amount));
            wallet.setUsableBalance(wallet.getUsableBalance().add(amount));
        }

        walletRepository.save(wallet);
        Transaction savedTx = transactionRepository.save(transaction);

        return TransactionMapper.toDto(savedTx);
    }

    public TransactionDto withdraw(Long walletId, BigDecimal amount,
                                 OppositePartyType destType, String destination) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (!wallet.isActiveForWithdraw()) {
            throw new RuntimeException("Wallet is not active for withdraw");
        }
        
        if (!wallet.isActiveForShopping()) {
            throw new RuntimeException("Wallet is not active for shopping");
        }

        if (wallet.getUsableBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setOppositePartyType(destType);
        transaction.setOppositeParty(destination);

        if (amount.compareTo(BigDecimal.valueOf(1000)) > 0) {
            transaction.setStatus(TransactionStatus.PENDING);
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(amount));
        } else {
            transaction.setStatus(TransactionStatus.APPROVED);
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(amount));
            wallet.setBalance(wallet.getBalance().subtract(amount));
        }

        walletRepository.save(wallet);
        Transaction savedTx = transactionRepository.save(transaction);

        return TransactionMapper.toDto(savedTx);
    }

    public List<TransactionDto> getTransactions(Long walletId, Principal  principal) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!wallet.getCustomer().getId().equals(user.getId())
                && !"EMPLOYEE".equals(user.getRole().toString())) {
            throw new RuntimeException("Unauthorized access to wallet");
        }

        return transactionRepository.findAllByWallet(wallet)
                .stream()
                .map(TransactionMapper::toDto)
                .toList();
    }
    
  
}
