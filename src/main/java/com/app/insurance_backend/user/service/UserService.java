package com.app.insurance_backend.user.service;

import com.app.insurance_backend.model.entity.AccountTransaction;
import com.app.insurance_backend.model.entity.BankAccount;
import com.app.insurance_backend.model.entity.User;
import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.TransactionType;
import com.app.insurance_backend.user.dto.BankAccountRequest;
import com.app.insurance_backend.user.dto.BankAccountResponse;
import com.app.insurance_backend.user.dto.DepositRequest;
import com.app.insurance_backend.user.dto.TransactionResponse;
import com.app.insurance_backend.user.repository.AccountTransactionRepository;
import com.app.insurance_backend.user.repository.BankAccountRepository;
import com.app.insurance_backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountTransactionRepository accountTransactionRepository;

    public BankAccountResponse addBankAccount(BankAccountRequest request) {
        User user = getAuthenticateUser();
        Optional<BankAccount> bankAccountExisted = bankAccountRepository.findByAccountNumber(request.getAccountNumber());

        if (bankAccountExisted.isPresent()) {
            throw new RuntimeException("Account number already exists");
        }

        BankAccount registerBankAccount = bankAccountRepository.save(BankAccount.builder()
                .user(user)
                .accountNumber(request.getAccountNumber())
                .bankName(request.getBankName())
                .availableBalance(request.getInitialBalance())
                .status(GeneralStatus.ACTIVE)
                .build());

        return mapToResponse(registerBankAccount);
    }

    public List<BankAccountResponse> getBankAccounts() {
        User user = getAuthenticateUser();

        return bankAccountRepository.findAllByUser_id(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionResponse deposit(Integer accountId, DepositRequest request) {
        User user = getAuthenticateUser();

        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Account does not belong to user");
        }

        account.setAvailableBalance(account.getAvailableBalance().add(request.getAmount()));
        bankAccountRepository.save(account);

        AccountTransaction transaction = accountTransactionRepository.save(
                AccountTransaction.builder()
                        .bankAccount(account)
                        .type(TransactionType.DEPOSIT)
                        .amount(request.getAmount())
                        .description(request.getDescription())
                        .transactionDate(LocalDateTime.now())
                        .build()
        );

        return mapToTransactionResponse(transaction);
    }

    public List<TransactionResponse> getTransactionsByAccount(Integer accountId) {
        User user = getAuthenticateUser();

        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Account does not belong to user");
        }

        return accountTransactionRepository.findByBankAccount_Id(account.getId())
                .stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    private BankAccountResponse mapToResponse(BankAccount account) {
        return BankAccountResponse.builder()
                .id(account.getId())
                .bankName(account.getBankName())
                .accountNumber(account.getAccountNumber())
                .availableBalance(account.getAvailableBalance())
                .status(account.getStatus())
                .createdAt(account.getCreated())
                .build();
    }

    private TransactionResponse mapToTransactionResponse(AccountTransaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

    private User getAuthenticateUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication()).getName();

        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
