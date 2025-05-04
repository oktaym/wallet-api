package com.example.wallet_api.controller;


import com.example.wallet_api.dto.ApiResponse;
import com.example.wallet_api.dto.TransactionDto;
import com.example.wallet_api.mapper.TransactionMapper;
import com.example.wallet_api.model.Transaction;
import com.example.wallet_api.model.enums.TransactionStatus;
import com.example.wallet_api.service.TransactionService;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<ApiResponse<TransactionDto>> approve(@PathVariable Long id, Principal principal) {
    	Transaction transaction = transactionService.updateTransactionStatus(id, TransactionStatus.APPROVED,principal);
        return ResponseEntity.ok(ApiResponse.success(TransactionMapper.toDto(transaction)));
    }

    @PatchMapping("/{id}/deny")
    public ResponseEntity<ApiResponse<TransactionDto>> deny(@PathVariable Long id,Principal principal) {
    	Transaction transaction = transactionService.updateTransactionStatus(id, TransactionStatus.DENIED,principal);
        return ResponseEntity.ok(ApiResponse.success(TransactionMapper.toDto(transaction)));
    }
}
