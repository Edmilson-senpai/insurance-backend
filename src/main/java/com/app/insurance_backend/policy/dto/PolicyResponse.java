package com.app.insurance_backend.policy.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PolicyResponse {
    private Integer id;
    private String policyNumber;
    private String insuranceName;
    private String planName;
    private GeneralStatus status;
    private LocalDate start;
    private LocalDate end;
    private BigDecimal totalAmount;
    private List<PolicyProductResponse> products;
}
