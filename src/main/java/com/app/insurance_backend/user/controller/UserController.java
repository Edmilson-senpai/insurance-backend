package com.app.insurance_backend.user.controller;

import com.app.insurance_backend.user.dto.BankAccountRequest;
import com.app.insurance_backend.user.dto.BankAccountResponse;
import com.app.insurance_backend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registerAccount")
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
}
