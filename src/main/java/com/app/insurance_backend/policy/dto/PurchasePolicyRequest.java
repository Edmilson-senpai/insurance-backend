package com.app.insurance_backend.policy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PurchasePolicyRequest {
    @NotNull(message = "Insurance Id is required")
    private Integer insuranceId;

    @NotNull(message = "Plan Id is required")
    private Integer planId;

    @NotNull(message = "Account Id is required")
    private Integer accountId;

    private List<Integer> productIds;
}
