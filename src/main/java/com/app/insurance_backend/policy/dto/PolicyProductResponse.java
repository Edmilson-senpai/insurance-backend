package com.app.insurance_backend.policy.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PolicyProductResponse {
    private Integer productId;
    private String productName;
    private BigDecimal price;
}
