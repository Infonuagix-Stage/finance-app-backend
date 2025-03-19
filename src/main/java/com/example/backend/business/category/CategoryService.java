package com.example.backend.business.category;

import com.example.backend.presentation.category.CategoryRequestDTO;
import com.example.backend.presentation.category.CategoryResponseDTO;
import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.category.CategoryType;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.category.CategoryRepository;
import com.example.backend.dataaccess.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public List<CategoryResponseDTO> getCategoriesByUser(String auth0UserId) {
        List<Category> categories = categoryRepository.findByUser_Auth0UserId(auth0UserId);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getCategoryByIdForUser(String auth0UserId, UUID categoryId) {
        Category category = categoryRepository.findByCategoryIdAndUser_Auth0UserId(categoryId, auth0UserId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + auth0UserId));
        return convertToDTO(category);
    }

    public CategoryResponseDTO createCategoryForUser(String auth0UserId, Category category) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with Auth0UserId: " + auth0UserId));

        if (category.getType() == null) {
            category.setType(CategoryType.EXPENSE);
        }

        category.setUser(user);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryResponseDTO createExpenseCategory(String auth0UserId, CategoryRequestDTO requestDTO) {
        User user = findUserById(auth0UserId);

        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());
        category.setType(CategoryType.EXPENSE);
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }

    public CategoryResponseDTO createIncomeCategory(String auth0UserId, CategoryRequestDTO requestDTO) {
        User user = findUserById(auth0UserId);

        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());
        category.setName(requestDTO.getName());
        category.setDescription(requestDTO.getDescription());
        category.setType(CategoryType.INCOME);
        category.setUser(user);

        Category savedCategory = categoryRepository.save(category);
        return convertToDTO(savedCategory);
    }
    public CategoryResponseDTO updateCategoryForUser(String auth0UserId, UUID categoryId, Category categoryDetails) {
        Category category = getCategoryByIdForUserEntity(auth0UserId, categoryId);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        if (categoryDetails.getType() != null) {
            category.setType(categoryDetails.getType());
        }
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTO(updatedCategory);
    }

    public void deleteCategoryForUser(String auth0UserId, UUID categoryId) {
        Category category = getCategoryByIdForUserEntity(auth0UserId, categoryId);
        categoryRepository.delete(category);
    }

    public List<CategoryResponseDTO> getAllCategories(String auth0UserId) {
        User user = findUserById(auth0UserId);
        List<Category> categories = categoryRepository.findByUser_Auth0UserId(auth0UserId);
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public User findUserById(String auth0UserId) {
        return userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + auth0UserId));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryResponseDTO convertToDTO(Category category) {
        return new CategoryResponseDTO(
                category.getCategoryId(),
                category.getName(),
                category.getDescription(),
                category.getCreationDate().toString(),
                category.getType().name(),
                category.getUser().getAuth0UserId()
        );
    }

    private Category getCategoryByIdForUserEntity(String auth0UserId, UUID categoryId) {
        return categoryRepository.findByCategoryIdAndUser_Auth0UserId(categoryId, auth0UserId)
                .orElseThrow(() -> new RuntimeException("Category not found for user with id " + auth0UserId));
    }

    public List<CategoryResponseDTO> getIncomeCategories(String auth0UserId) {
        List<Category> categories = categoryRepository.findByUser_Auth0UserIdAndType(auth0UserId, CategoryType.INCOME);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDTO> getExpenseCategories(String auth0UserId) {
        List<Category> categories = categoryRepository.findByUser_Auth0UserIdAndType(auth0UserId, CategoryType.EXPENSE);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Category convertToEntity(CategoryRequestDTO dto, User user) {
        Category category = new Category();
        category.setCategoryId(UUID.randomUUID());
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setType(CategoryType.valueOf(dto.getType().toUpperCase()));
        category.setUser(user);
        return category;
    }

}
