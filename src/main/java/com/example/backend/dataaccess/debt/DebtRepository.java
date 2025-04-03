package com.example.backend.dataaccess.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DebtRepository extends JpaRepository<Debt, UUID> {
    List<Debt> findByUser_Auth0UserId(String auth0UserId);

    Optional<Debt> findByDebtId(UUID debtId);

    Optional<Debt> findByDebtIdAndUser_Auth0UserId(UUID debtId, String auth0UserId);

    void deleteByDebtId(UUID debtId);
}
