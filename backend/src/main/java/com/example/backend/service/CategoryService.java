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

    // Récupère toutes les catégories pour un utilisateur par son ID
    public List<CategoryResponseDTO> getCategoriesByUser(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupère une catégorie par son ID pour un utilisateur
    public CategoryResponseDTO getCategoryByIdForUser(Long userId, Long categoryId) {
        Category category = categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
        return convertToDTO(category);
    }

    // Méthode générique pour créer une catégorie pour un utilisateur
    public CategoryResponseDTO createCategoryForUser(Long userId, Category category) {
        User user = findUserById(userId);
        // Si le type n'est pas renseigné, on affecte EXPENSE par défaut
        if (category.getType() == null) {
            category.setType(CategoryType.EXPENSE);
        }
        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    // Crée une catégorie de dépense pour un utilisateur
    public CategoryResponseDTO createExpenseCategory(Long userId, Category category) {
        return null;
    }

    // Crée une catégorie de revenu (income) pour un utilisateur
    public CategoryResponseDTO createIncomeCategory(Long userId, Category category) {
        User user = findUserById(userId);
        category.setUser(user);
        category.setType(CategoryType.INCOME);
        Category savedCategory = saveCategory(category);
        return convertToDTO(savedCategory);
    }

    // Méthode pour mettre à jour une catégorie pour un utilisateur
    public CategoryResponseDTO updateCategoryForUser(Long userId, Long categoryId, Category categoryDetails) {
        Category category = getCategoryByIdForUserEntity(userId, categoryId);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        // Vous pouvez mettre à jour le type si nécessaire
        if (categoryDetails.getType() != null) {
            category.setType(categoryDetails.getType());
        }
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    // Supprime une catégorie pour un utilisateur
    public void deleteCategoryForUser(Long userId, Long categoryId) {
        Category category = getCategoryByIdForUserEntity(userId, categoryId);
        categoryRepository.delete(category);
    }

    // Récupère une catégorie par son nom pour un utilisateur
    public CategoryResponseDTO getCategoryByName(Long userId, String categoryName) {
        Category category = (Category) categoryRepository.findByUserIdAndName(userId, categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found with name " + categoryName));
        return convertToDTO(category);
    }

    // Récupère toutes les catégories d'un utilisateur en passant par l'entité User
    public List<CategoryResponseDTO> getAllCategories(Long userId) {
        User user = findUserById(userId);
        List<Category> categories = categoryRepository.findByUser(user);
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Méthode utilitaire pour récupérer un utilisateur par son ID
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }

    // Méthode demandée pour sauvegarder une catégorie
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Méthode privée de conversion de l'entité Category en CategoryResponseDTO
    public CategoryResponseDTO convertToDTO(Category category) {
        return new CategoryResponseDTO(
                category.getName(),
                category.getId(),
                category.getDescription(),
                category.getCreationDate().toString(),
                category.getType().name(),  // Conversion de l'enum en chaîne
                category.getUser() != null ? category.getUser().getId() : null
        );
    }

    // Méthode privée utilitaire pour récupérer une entité Category pour usage interne
    private Category getCategoryByIdForUserEntity(Long userId, Long categoryId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
    }

    public List<CategoryResponseDTO> getIncomeCategories(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdAndType(userId, CategoryType.INCOME);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getExpenseCategories(Long userId) {
        List<Category> categories = categoryRepository.findByUserIdAndType(userId, CategoryType.EXPENSE);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
