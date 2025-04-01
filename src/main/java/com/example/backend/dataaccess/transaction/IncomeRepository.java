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
public interface IncomeRepository extends JpaRepository<Income, UUID> {

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Income e WHERE e.user.auth0UserId = :auth0UserId AND e.category.categoryId = :categoryId")
    BigDecimal getTotalForCategory(@Param("auth0UserId") String auth0UserId,
                                   @Param("categoryId") UUID categoryId);

    List<Income> findByUser_Auth0UserIdAndCategory_CategoryId(String auth0UserId, UUID categoryId);

    Optional<Income> findByIncomeId(UUID incomeId);


    @Query("SELECT i FROM Income i WHERE i.user.auth0UserId = :auth0UserId AND YEAR(i.incomeDate)=:year AND MONTH(i.incomeDate)=:month")
    List<Income> findByUserYearAndMonth(@Param("auth0UserId") String auth0UserId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT i FROM Income i WHERE i.user.auth0UserId = :auth0UserId AND i.category.categoryId = :categoryId AND YEAR(i.incomeDate)=:year AND MONTH(i.incomeDate)=:month")
    List<Income> findByUserYearMonthAndCategory(@Param("auth0UserId") String auth0UserId, @Param("year") int year, @Param("month") int month, @Param("categoryId") UUID categoryId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Income e " +
            "WHERE e.user.auth0UserId = :auth0UserId " +
            "  AND e.category.categoryId = :categoryId " +
            "  AND YEAR(e.incomeDate) = :year " +
            "  AND MONTH(e.incomeDate) = :month")
    BigDecimal getTotalForCategoryYearMonth(@Param("auth0UserId") String auth0UserId,
                                            @Param("categoryId") UUID categoryId,
                                            @Param("year") int year,
                                            @Param("month") int month);

}
