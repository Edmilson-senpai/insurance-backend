package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.InsuranceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsuranceRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;

    @NotNull(message = "Type is required")
    private InsuranceType type;

    @NotNull(message = "Status is required")
    private GeneralStatus status;
}
