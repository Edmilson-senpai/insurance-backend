package com.app.insurance_backend.policy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProductsRequest {
    @NotNull(message = "Account Id is required")
    private Integer accountId;

    @NotEmpty(message = "At least one product is required")
    private List<Integer> productIds;
}
