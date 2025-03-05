package com.example.backend.dataaccess.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE i.user.id = :userId AND i.category.id = :categoryId")
    BigDecimal getTotalForCategory(@Param("userId") UUID userId, @Param("categoryId") UUID categoryId);

    List<Income> findByUser_UserIdAndCategory_CategoryId(UUID userId, UUID categoryId);

    Optional<Income> findByIncomeId(UUID incomeId);

}
