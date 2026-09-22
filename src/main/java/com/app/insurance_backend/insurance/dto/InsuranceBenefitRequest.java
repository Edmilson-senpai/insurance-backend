package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.BenefitType;
import com.app.insurance_backend.model.enums.GeneralStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InsuranceBenefitRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Type is required")
    private BenefitType type;

    @NotNull(message = "Status is required")
    private GeneralStatus status;
}
