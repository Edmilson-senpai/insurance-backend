package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.BenefitType;
import com.app.insurance_backend.model.enums.GeneralStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InsuranceBenefitResponse {
    private Integer id;
    private String name;
    private String description;
    private BenefitType type;
    private GeneralStatus status;
}
