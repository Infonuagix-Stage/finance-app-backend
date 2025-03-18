package com.example.backend.dataaccess.category;

import com.example.backend.dataaccess.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryIdAndUser_Auth0UserId(UUID categoryId, String auth0UserId);

    Optional<Category> findByCategoryId(UUID categoryId);

    List<Category> findByUser_Auth0UserId(String auth0UserId);

    List<Category> findByUser_Auth0UserIdAndType(String auth0UserId, CategoryType type);
}

