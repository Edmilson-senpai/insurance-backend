package com.app.insurance_backend.insurance.repository;

import com.app.insurance_backend.model.entity.InsuranceBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceBenefitRepository extends JpaRepository<InsuranceBenefit, Integer> {
    List<InsuranceBenefit> findByInsurance_Id(Integer insuranceId);
}
