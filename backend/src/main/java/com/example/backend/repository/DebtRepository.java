package com.example.backend.repository;

import com.example.backend.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByUserId(Long userId); // Trouver toutes les dettes d'un utilisateur

    Optional<Debt> findByIdAndUserId(Long debtId, Long userId);
}