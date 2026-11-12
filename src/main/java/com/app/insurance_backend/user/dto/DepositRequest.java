package com.app.insurance_backend.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequest {
    @NotNull(message = "Initial amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;
}
