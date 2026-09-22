package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.PlanType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InsurancePlanResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private PlanType type;
    private GeneralStatus status;
}
