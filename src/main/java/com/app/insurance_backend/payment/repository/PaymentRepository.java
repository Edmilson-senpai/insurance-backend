package com.app.insurance_backend.payment.repository;

import com.app.insurance_backend.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByPolicy_User_Id(Integer userId);
    List<Payment> findByPolicy_Id(Integer policyId);
}
