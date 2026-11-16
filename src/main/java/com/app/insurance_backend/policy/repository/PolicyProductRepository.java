package com.app.insurance_backend.policy.repository;

import com.app.insurance_backend.model.entity.InsuranceProduct;
import com.app.insurance_backend.model.entity.Policy;
import com.app.insurance_backend.model.entity.PolicyProduct;
import com.app.insurance_backend.model.entity.PolicyProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyProductRepository extends JpaRepository<PolicyProduct, PolicyProductId> {
    List<PolicyProduct> findByPolicy(Policy policy);
    boolean existsByPolicyAndInsuranceProduct(Policy policy, InsuranceProduct product);
}
