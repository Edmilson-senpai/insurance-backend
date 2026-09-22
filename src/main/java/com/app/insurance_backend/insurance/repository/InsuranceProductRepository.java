package com.app.insurance_backend.insurance.repository;

import com.app.insurance_backend.model.entity.InsuranceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceProductRepository extends JpaRepository<InsuranceProduct, Integer> {
    List<InsuranceProduct> findByInsurance_Id(Integer insuranceId);

    List<InsuranceProduct> findByParentProduct_Id(Integer parentId);

    List<InsuranceProduct> findByParentProductIsNull();
}
