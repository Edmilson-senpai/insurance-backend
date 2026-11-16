package com.app.insurance_backend.payment.dto;

import com.app.insurance_backend.model.enums.PaymentMethod;
import com.app.insurance_backend.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Integer id;
    private String policyNumber;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentMethod method;
    private PaymentStatus status;
}
