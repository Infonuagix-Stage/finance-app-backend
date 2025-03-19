package com.example.backend.dataaccess.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {


    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.user.auth0UserId = :auth0UserId AND e.category.categoryId = :categoryId")
    BigDecimal getTotalForCategory(@Param("auth0UserId") String auth0UserId,
                                   @Param("categoryId") UUID categoryId);

    List<Expense> findByUser_Auth0UserIdAndCategory_CategoryId(String auth0UserId , UUID categoryId);


}

