package com.app.insurance_backend.insurance.dto;

import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.ProductType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class InsuranceProductResponse {
    private List<InsuranceProductResponse> subProducts;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductType type;
    private GeneralStatus status;
}
