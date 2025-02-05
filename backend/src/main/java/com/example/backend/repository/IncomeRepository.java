package com.example.backend.repository;

import com.example.backend.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUser_IdAndCategory_Id(Long userId, Long categoryId);

    @Query("SELECT COALESCE(SUM(i.amount), 0) FROM Income i WHERE i.user.id = :userId AND i.category.id = :categoryId")
    BigDecimal getTotalForCategory(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    List<Income> findByUserIdAndCategoryId(Long userId, Long categoryId);
}
