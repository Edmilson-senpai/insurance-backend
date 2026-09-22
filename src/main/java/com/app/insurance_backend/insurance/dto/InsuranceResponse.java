package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.InsuranceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InsuranceResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private InsuranceType type;
    private GeneralStatus status;
}
