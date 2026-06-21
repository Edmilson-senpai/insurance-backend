package com.app.insurance_backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "policy_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"policy", "insuranceProduct"})
@EqualsAndHashCode(exclude = {"policy", "insuranceProduct"})
@IdClass(PolicyProductId.class)
public class PolicyProduct {
    @Id
    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    private Policy policy;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private InsuranceProduct insuranceProduct;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
