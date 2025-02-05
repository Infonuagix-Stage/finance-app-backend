package com.example.backend.repository;

import com.example.backend.dto.CategoryResponseDTO;
import com.example.backend.model.Category;
import com.example.backend.model.CategoryType;
import com.example.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Find a category by ID and associated user ID
    Optional<Category> findByIdAndUserId(Long id, Long userId);


    List<Category> findByUserId(Long userId);

    Optional<Object> findByUserIdAndName(Long userId, String categoryName);

    List<Category> findByUser(User user);


    List<Category> findByUserIdAndType(Long userId, CategoryType categoryType);
}
