package com.example.backend.business.category;

import com.example.backend.presentation.category.CategoryRequestDTO;
import com.example.backend.presentation.category.CategoryResponseDTO;
import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.category.CategoryType;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.category.CategoryRepository;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
    public List<CategoryResponseDTO> getCategoriesByUser(UUID userId) {
        List<Category> categories = categoryRepository.findByUser_UserId(userId);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupère une catégorie par son ID pour un utilisateur
    public CategoryResponseDTO getCategoryByIdForUser(UUID userId, UUID categoryId) {
        Category category = categoryRepository.findByCategoryIdAndUser_UserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
        return convertToDTO(category);
    }

    // Méthode générique pour créer une catégorie pour un utilisateur
    public CategoryResponseDTO createCategoryForUser(UUID userId, Category category) {
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
    public CategoryResponseDTO createExpenseCategory(UUID userId, Category category) {
        return null;
    }

    // Crée une catégorie de revenu (income) pour un utilisateur
    public CategoryResponseDTO createIncomeCategory(UUID userId, Category category) {
        User user = findUserById(userId);
        category.setUser(user);
        category.setType(CategoryType.INCOME);
        Category savedCategory = saveCategory(category);
        return convertToDTO(savedCategory);
    }

    // Méthode pour mettre à jour une catégorie pour un utilisateur
    public CategoryResponseDTO updateCategoryForUser(UUID userId, UUID categoryId, Category categoryDetails) {
        Category category = getCategoryByIdForUserEntity(userId, categoryId);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        if (categoryDetails.getType() != null) {
            category.setType(categoryDetails.getType());
        }
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    // Supprime une catégorie pour un utilisateur
    public void deleteCategoryForUser(UUID userId, UUID categoryId) {
        Category category = getCategoryByIdForUserEntity(userId, categoryId);
        categoryRepository.delete(category);
    }

//    // Récupère une catégorie par son nom pour un utilisateur
//    public CategoryResponseDTO getCategoryByName(UUID userId, String categoryName) {
//        Category category = (Category) categoryRepository.findByUserIdAndName(userId, categoryName)
//                .orElseThrow(() -> new RuntimeException("Category not found with name " + categoryName));
//        return convertToDTO(category);
//    }

    // Récupère toutes les catégories d'un utilisateur en passant par l'entité User
    public List<CategoryResponseDTO> getAllCategories(UUID userId) {
        User user = findUserById(userId);
        List<Category> categories = categoryRepository.findByUser_UserId(userId);
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Méthode utilitaire pour récupérer un utilisateur par son ID
    public User findUserById(UUID userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
    }


    // Méthode demandée pour sauvegarder une catégorie
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Méthode privée de conversion de l'entité Category en CategoryResponseDTO
    public CategoryResponseDTO convertToDTO(Category category) {
        return new CategoryResponseDTO(
                category.getCategoryId(),
                category.getName(),
                category.getDescription(),
                category.getCreationDate().toString(),
                category.getType().name(),  // Conversion de l'enum en chaîne
                category.getUser() != null ? category.getUser().getUserId() : null
        );
    }

    // Méthode privée utilitaire pour récupérer une entité Category pour usage interne
    private Category getCategoryByIdForUserEntity(UUID userId, UUID categoryId) {
        return categoryRepository.findByCategoryIdAndUser_UserId(categoryId, userId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + userId));
    }

    public List<CategoryResponseDTO> getIncomeCategories(UUID userId) {
        List<Category> categories = categoryRepository.findByUser_UserIdAndType(userId, CategoryType.INCOME);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getExpenseCategories(UUID userId) {
        List<Category> categories = categoryRepository.findByUser_UserIdAndType(userId, CategoryType.EXPENSE);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Category convertToEntity(CategoryRequestDTO dto, User user) {
        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());  // Génère un UUID unique
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setType(CategoryType.valueOf(dto.getType().toUpperCase()));  // Convertir la String en Enum
        category.setUser(user);  // Associe l'utilisateur
        return category;
    }

}
