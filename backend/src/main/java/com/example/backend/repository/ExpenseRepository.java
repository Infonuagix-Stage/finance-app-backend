package com.example.backend.repository;

import com.example.backend.model.Category;
import com.example.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser_IdAndCategory_Id(Long userId, Long categoryId);



    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.id = :userId AND e.category.id = :categoryId")
    BigDecimal getTotalForCategory(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);


}

