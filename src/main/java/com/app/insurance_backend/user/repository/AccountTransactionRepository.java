package com.app.insurance_backend.user.repository;

import com.app.insurance_backend.model.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Integer> {
    List<AccountTransaction> findByBankAccount_Id(Integer accountId);
}
