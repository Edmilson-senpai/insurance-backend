package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.PlanType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsurancePlanRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Type is required")
    private PlanType type;

    @NotNull(message = "Status is required")
    private GeneralStatus status;
}
