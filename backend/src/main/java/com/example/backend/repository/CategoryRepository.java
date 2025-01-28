package com.example.backend.repository;

import com.example.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Find a category by ID and associated user ID
    Optional<Category> findByIdAndUserId(Integer id, Integer userId);


    List<Category> findByUserId(Integer userId);
}
