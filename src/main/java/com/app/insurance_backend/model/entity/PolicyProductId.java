package com.app.insurance_backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyProductId implements Serializable {
    private Integer policy;
    private Integer insuranceProduct;
}
