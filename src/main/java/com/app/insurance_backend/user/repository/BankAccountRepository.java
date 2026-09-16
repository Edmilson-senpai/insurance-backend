package com.app.insurance_backend.user.repository;

import com.app.insurance_backend.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    List<BankAccount> findAllByUser_id(Integer id);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
