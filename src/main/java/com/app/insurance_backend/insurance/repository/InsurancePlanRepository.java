package com.app.insurance_backend.insurance.repository;

import com.app.insurance_backend.model.entity.InsurancePlan;
import com.app.insurance_backend.model.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Integer> {
    List<InsurancePlan> findByInsurance_Id(Integer insuranceId);

    List<InsurancePlan> findByInsurance_IdAndStatus(Integer insuranceId, GeneralStatus status);
}
