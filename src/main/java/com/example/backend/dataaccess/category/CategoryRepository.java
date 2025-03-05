package com.example.backend.dataaccess.category;

import com.example.backend.dataaccess.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    // Find a category by ID and associated user ID
    Optional<Category> findByCategoryIdAndUser_UserId(UUID categoryId, UUID userId);


    //Optional<Object> findByUserIdAndName(UUID userId, String categoryName);
    List<Category> findByUser_UserId(UUID userId);


    List<Category> findByUser_UserIdAndType(UUID userId, CategoryType type);

}
