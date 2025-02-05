package com.example.backend.service;

import com.example.backend.dto.CategoryResponseDTO;
import com.example.backend.model.Category;
import com.example.backend.model.CategoryType;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Get all categories for a specific user
    public List<CategoryResponseDTO> getCategoriesByUser(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a category by ID for a specific user
    public CategoryResponseDTO getCategoryByIdForUser(Long userId, Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
        return convertToDTO(category);
    }

    // Create a new category for a specific user
    public CategoryResponseDTO createCategoryForUser(Long userId, Category category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // Si le type n'est pas renseigné, on affecte une valeur par défaut
        if (category.getType() == null) {
            category.setType(CategoryType.EXPENSE); // Par exemple, EXPENSE par défaut
        }

        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }


    // Update a category for a specific user
    public CategoryResponseDTO updateCategoryForUser(Long userId, Long categoryId, Category categoryDetails) {
        Category category = getCategoryByIdForUserEntity(userId, categoryId);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    // Delete a category for a specific user
    public void deleteCategoryForUser(Long userId, Long categoryId) {
        Category category = getCategoryByIdForUserEntity(userId, categoryId);
        categoryRepository.delete(category);
    }

    // Private helper method to convert a Category entity to a DTO
    private CategoryResponseDTO convertToDTO(Category category) {
        return new CategoryResponseDTO(
                category.getName(),
                category.getId(),
                category.getDescription(),
                category.getCreationDate().toString(),
                category.getType().name(),   // Convertit l'enum en chaîne
                category.getUser() != null ? category.getUser().getId() : null  // Récupère l'ID de l'utilisateur
        );
    }



    // Private helper method to fetch a Category entity for internal use
    private Category getCategoryByIdForUserEntity(Long userId, Long categoryId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
    }

    // Exemple de méthode de mapping d'entité vers DTO
    private CategoryResponseDTO mapToResponseDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        // Ajoutez d'autres champs si nécessaire
        return dto;
    }

    public CategoryResponseDTO getCategoryByName(Long userId, String categoryName) {
        Category category = (Category) categoryRepository.findByUserIdAndName(userId, categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name " + categoryName));
        return mapToResponseDTO(category);
    }

    public List<CategoryResponseDTO> getAllCategories(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        List<Category> categories = categoryRepository.findByUser(user);
        return categories.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }
}
