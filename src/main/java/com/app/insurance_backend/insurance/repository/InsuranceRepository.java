package com.app.insurance_backend.insurance.repository;

import com.app.insurance_backend.model.entity.Insurance;
import com.app.insurance_backend.model.enums.GeneralStatus;
import com.app.insurance_backend.model.enums.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
    List<Insurance> findByType(InsuranceType type);

    List<Insurance> findByStatus(GeneralStatus status);
}
