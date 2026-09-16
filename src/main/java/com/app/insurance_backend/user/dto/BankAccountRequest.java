package com.app.insurance_backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountRequest {
    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Initial balance is required")
    @Positive(message = "Balance must be positive")
    private BigDecimal initialBalance;
}
