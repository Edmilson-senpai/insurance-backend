package com.app.insurance_backend.user.dto;

import com.app.insurance_backend.model.enums.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {
    private Integer id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
}
