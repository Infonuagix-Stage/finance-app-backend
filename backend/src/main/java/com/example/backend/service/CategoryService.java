package com.example.backend.service;

import com.example.backend.model.Category;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Get all categories for a specific user
    public List<Category> getCategoriesByUser(Integer userId) {
        return categoryRepository.findByUserId(userId);
    }

    // Get a category by ID for a specific user
    public Category getCategoryByIdForUser(Integer userId, Integer categoryId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
    }

    // Create a new category for a specific user
    public Category createCategoryForUser(Integer userId, Category category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        category.setUser(user);
        return categoryRepository.save(category);
    }

    // Update a category for a specific user
    public Category updateCategoryForUser(Integer userId, Integer categoryId, Category categoryDetails) {
        Category category = getCategoryByIdForUser(userId, categoryId);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        return categoryRepository.save(category);
    }

    // Delete a category for a specific user
    public void deleteCategoryForUser(Integer userId, Integer categoryId) {
        Category category = getCategoryByIdForUser(userId, categoryId);
        categoryRepository.delete(category);
    }
}

