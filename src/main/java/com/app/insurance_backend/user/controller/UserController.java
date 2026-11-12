package com.app.insurance_backend.user.controller;

import com.app.insurance_backend.user.dto.BankAccountRequest;
import com.app.insurance_backend.user.dto.BankAccountResponse;
import com.app.insurance_backend.user.dto.DepositRequest;
import com.app.insurance_backend.user.dto.TransactionResponse;
import com.app.insurance_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/account")
    public ResponseEntity<BankAccountResponse> setBankAccount(
            @RequestBody @Valid BankAccountRequest bankAccountRequest) {
        BankAccountResponse bankAccountResponse;

        bankAccountResponse = userService.addBankAccount(bankAccountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountResponse);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<BankAccountResponse>> getBankAccounts () {
        List<BankAccountResponse> bankAccountsResponse;

        bankAccountsResponse = userService.getBankAccounts();

        return ResponseEntity.ok(bankAccountsResponse);
    }

    @PostMapping("/accounts/{accountId}/deposits")
    public ResponseEntity<TransactionResponse> setDeposit(
            @PathVariable Integer accountId,
            @RequestBody @Valid DepositRequest request) {
        TransactionResponse transactionResponse;

        transactionResponse = userService.deposit(accountId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccount (
            @PathVariable Integer accountId) {
        List<TransactionResponse> transactionResponse;

        transactionResponse = userService.getTransactionsByAccount(accountId);

        return ResponseEntity.ok(transactionResponse);
    }
}
