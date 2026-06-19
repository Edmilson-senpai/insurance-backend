package com.app.insurance_backend.model.entity;

import com.app.insurance_backend.model.enums.ProductStatus;
import com.app.insurance_backend.model.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "insurance_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"insurance", "parentProduct", "subProducts"})
@EqualsAndHashCode(exclude = {"insurance", "parentProduct", "subProducts"})
public class InsuranceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    private Insurance insurance;

    @ManyToOne
    @JoinColumn(name = "parent_product_id")
    private InsuranceProduct parentProduct;

    @OneToMany(mappedBy = "parentProduct")
    private List<InsuranceProduct> subProducts;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
}
