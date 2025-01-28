package com.example.backend.controller;

import com.example.backend.model.Category;
import com.example.backend.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories for a user
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(@PathVariable Integer userId) {
        List<Category> categories = categoryService.getCategoriesByUser(userId);
        return ResponseEntity.ok(categories);
    }

    // Get a category by ID for a user
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer userId, @PathVariable Integer id) {
        Category category = categoryService.getCategoryByIdForUser(userId, id);
        return ResponseEntity.ok(category);
    }

    // Create a new category for a user
    @PostMapping
    public ResponseEntity<Category> createCategory(@PathVariable Integer userId, @RequestBody Category category) {
        Category createdCategory = categoryService.createCategoryForUser(userId, category);
        return ResponseEntity.ok(createdCategory);
    }

    // Update a category by ID for a user
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Integer userId,
            @PathVariable Integer id,
            @RequestBody Category categoryDetails
    ) {
        Category updatedCategory = categoryService.updateCategoryForUser(userId, id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category by ID for a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer userId, @PathVariable Integer id) {
        categoryService.deleteCategoryForUser(userId, id);
        return ResponseEntity.noContent().build();
    }
}

