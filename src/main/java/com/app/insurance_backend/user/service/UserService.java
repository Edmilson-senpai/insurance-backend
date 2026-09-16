package com.app.insurance_backend.user.service;

import com.app.insurance_backend.model.entity.BankAccount;
import com.app.insurance_backend.model.entity.User;
import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.user.dto.BankAccountRequest;
import com.app.insurance_backend.user.dto.BankAccountResponse;
import com.app.insurance_backend.user.repository.BankAccountRepository;
import com.app.insurance_backend.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

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

    private User getAuthenticateUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication()).getName();

        return userRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
