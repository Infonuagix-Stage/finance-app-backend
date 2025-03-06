package com.example.backend.dataaccess.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import java.util.UUID;

@Repository
public interface DebtRepository extends JpaRepository<Debt, UUID> {
    List<Debt> findByUserId(UUID userId);




    Optional<Debt> findByDebtIdAndUserId(UUID debtId, UUID userId);


    void deleteByDebtId(UUID debtId);
}