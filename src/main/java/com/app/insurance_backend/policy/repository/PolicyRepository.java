package com.app.insurance_backend.policy.repository;

import com.app.insurance_backend.model.entity.Policy;
import com.app.insurance_backend.model.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Integer> {
    List<Policy> findByUser_Id(Integer userId);
    Optional<Policy> findByUser_IdAndInsurance_IdAndStatus(Integer userId, Integer insuranceId, GeneralStatus status);
}
