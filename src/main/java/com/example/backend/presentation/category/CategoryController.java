package com.example.backend.presentation.category;

import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.category.CategoryType;
import com.example.backend.dataaccess.user.User;
import com.example.backend.business.category.CategoryService;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserRepository userRepository;

    public CategoryController(CategoryService categoryService, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(@PathVariable("userId") UUID userId) {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories(userId);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/expense")
    public ResponseEntity<List<CategoryResponseDTO>> getExpensesCategories(
            @PathVariable("userId") UUID userId) {
        List<CategoryResponseDTO> expenses = categoryService.getExpenseCategories(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/income")
    public ResponseEntity<List<CategoryResponseDTO>> getIncomesCategories(
            @PathVariable("userId") UUID userId){
        List<CategoryResponseDTO> incomes = categoryService.getIncomeCategories(userId);
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable UUID userId, @PathVariable UUID id) {
        CategoryResponseDTO category = categoryService.getCategoryByIdForUser(userId, id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @PathVariable UUID userId,
            @RequestBody CategoryRequestDTO requestDTO) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Category category = categoryService.convertToEntity(requestDTO, user);
        Category savedCategory = categoryService.saveCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.convertToDTO(savedCategory));
    }

    @PostMapping("/expense")
    public ResponseEntity<CategoryResponseDTO> createExpenseCategory(
            @PathVariable UUID userId,
            @RequestBody CategoryRequestDTO requestDTO) {

        CategoryResponseDTO createdCategory = categoryService.createExpenseCategory(userId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PostMapping("/income")
    public ResponseEntity<CategoryResponseDTO> createIncomeCategory(
            @PathVariable UUID userId,
            @RequestBody CategoryRequestDTO requestDTO) {

        CategoryResponseDTO createdCategory = categoryService.createIncomeCategory(userId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable UUID userId,
            @PathVariable UUID id,
            @RequestBody Category categoryDetails) {
        try {
            CategoryResponseDTO updatedCategory = categoryService.updateCategoryForUser(userId, id, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID userId, @PathVariable UUID id) {
        try {
            categoryService.deleteCategoryForUser(userId, id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
