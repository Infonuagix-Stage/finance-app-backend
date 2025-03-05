package com.example.backend.dataaccess.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {


    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.category.id = :categoryId")
    BigDecimal getTotalForCategory(@Param("userId") UUID userId, @Param("categoryId") UUID categoryId);

    List<Expense> findByUser_UserIdAndCategory_CategoryId(UUID userId, UUID categoryId);


}

