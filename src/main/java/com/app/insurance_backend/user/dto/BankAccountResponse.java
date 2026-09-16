package com.app.insurance_backend.user.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BankAccountResponse {
    private Integer id;
    private String bankName;
    private String accountNumber;
    private BigDecimal availableBalance;
    private GeneralStatus status;
    private LocalDateTime createdAt;
}
