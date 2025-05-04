package com.example.wallet_api.controller;

import com.example.wallet_api.dto.*;
import com.example.wallet_api.model.Transaction;
import com.example.wallet_api.model.User;
import com.example.wallet_api.model.Wallet;
import com.example.wallet_api.model.enums.OppositePartyType;
import com.example.wallet_api.repository.UserRepository;
import com.example.wallet_api.service.WalletService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;
    private final UserRepository userRepository;

    public WalletController(WalletService walletService, UserRepository userRepository) {
        this.walletService = walletService;
        this.userRepository= userRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WalletDto>> createWallet(@RequestBody CreateWalletRequest request,
                                                            Principal principal) {
    	WalletDto wallet = walletService.createWallet(
                request.getWalletName(),
                request.getCurrency(),
                request.isForShopping(),
                request.isForWithdraw(),
                principal.getName());
        return ResponseEntity.ok(ApiResponse.success(wallet));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WalletDto>>> getWallets(Principal principal) {
        List<WalletDto> wallets = walletService.listWallets(principal);
        return ResponseEntity.ok(ApiResponse.success(wallets));
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<ApiResponse<TransactionDto>> deposit(@PathVariable Long walletId,
    														@RequestBody DepositRequest request,
    														Principal principal) {
    	 User user = userRepository.findByUsername(principal.getName())
    	            .orElseThrow(() -> new RuntimeException("User not found"));

    	 WalletDto walletDto = walletService.getWalletById(walletId, principal);
    	    
    	    if (!"EMPLOYEE".equals(user.getRole().toString()) &&
    	            !walletDto.getCustomer().getId().equals(user.getId())) {
    	            throw new RuntimeException("Unauthorized access to this wallet");
    	        }

    	  TransactionDto tx = walletService.deposit(walletId,
    	            request.getAmount(),
    	            request.getSourceType(),
    	            request.getSource());
        return ResponseEntity.ok(ApiResponse.success(tx));
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<ApiResponse<TransactionDto>> withdraw(@PathVariable Long walletId,
    															@RequestBody WithdrawRequest request,
    															Principal principal) 
    {
    	 User user = userRepository.findByUsername(principal.getName())
 	            .orElseThrow(() -> new RuntimeException("User not found"));

    	 WalletDto walletDto = walletService.getWalletById(walletId, principal);
 	    
 	    if (!"EMPLOYEE".equals(user.getRole().toString()) &&
 	            !walletDto.getCustomer().getId().equals(user.getId())) {
 	            throw new RuntimeException("Unauthorized access to this wallet");
 	        }
    	TransactionDto tx = walletService.withdraw(walletId,
    	            request.getAmount(),
    	            request.getDestType(),
    	            request.getDestination());
        return ResponseEntity.ok(ApiResponse.success(tx));
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getTransactions(@PathVariable Long walletId,
                                                                          Principal principal) {
        List<TransactionDto> txList = walletService.getTransactions(walletId, principal);
        return ResponseEntity.ok(ApiResponse.success(txList));
    }
}
