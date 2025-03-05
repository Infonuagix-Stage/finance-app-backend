package com.example.backend.dataaccess.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByUserId(Long userId);

    Optional<Debt> findByIdAndUserId(Long debtId, Long userId);
}