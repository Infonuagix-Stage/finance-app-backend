package com.example.backend.repository;

import com.example.backend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByUser_IdAndCategory_Id(Long userId, Long categoryId);

}
