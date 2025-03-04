package com.example.backend.presentation.category;

import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.category.CategoryType;
import com.example.backend.dataaccess.user.User;
import com.example.backend.business.category.CategoryService;
import org.springframework.http.HttpStatus;
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

    // GET : Récupérer toutes les catégories pour un utilisateur
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(@PathVariable("userId") Long userId) {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories(userId);
        return ResponseEntity.ok(categories);
    }

    // GET : Récupérer les catégories expense
    @GetMapping("/expense")
    public ResponseEntity<List<CategoryResponseDTO>> getExpensesCategories(
            @PathVariable("userId") Long userId) {
        List<CategoryResponseDTO> expenses = categoryService.getExpenseCategories(userId);
        return ResponseEntity.ok(expenses);
    }

    // GET : Récupérer les catégories expense
    @GetMapping("/income")
    public ResponseEntity<List<CategoryResponseDTO>> getIncomesCategories(
            @PathVariable("userId") Long userId){
        List<CategoryResponseDTO> incomes = categoryService.getIncomeCategories(userId);
        return ResponseEntity.ok(incomes);
    }


    // GET : Récupérer une catégorie par ID pour un utilisateur
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long userId, @PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryByIdForUser(userId, id);
        return ResponseEntity.ok(category);
    }

    // POST : Créer une catégorie (endpoint général si nécessaire)
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@PathVariable Long userId, @RequestBody Category category) {
        CategoryResponseDTO createdCategory = categoryService.createCategoryForUser(userId, category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // POST : Créer une catégorie de dépense
    @PostMapping("/expense")
    public ResponseEntity<CategoryResponseDTO> createExpenseCategory(@PathVariable Long userId, @RequestBody Category category) {
        User user = categoryService.findUserById(userId); // ou utilisez userRepository directement dans le service
        category.setUser(user);
        category.setType(CategoryType.EXPENSE);
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.convertToDTO(savedCategory));
    }

    // POST : Créer une catégorie de revenu (ou entrée)
    @PostMapping("/income")
    public ResponseEntity<CategoryResponseDTO> createIncomeCategory(@PathVariable Long userId, @RequestBody Category category) {
        User user = categoryService.findUserById(userId);
        category.setUser(user);
        category.setType(CategoryType.INCOME);
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.convertToDTO(savedCategory));
    }

    // PUT : Mise à jour d'une catégorie par ID pour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody Category categoryDetails
    ) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategoryForUser(userId, id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // DELETE : Suppression d'une catégorie par ID pour un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long userId, @PathVariable Long id) {
        categoryService.deleteCategoryForUser(userId, id);
        return ResponseEntity.noContent().build();
    }

    // (Optionnel) GET : Récupérer une catégorie par nom
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(
            @PathVariable("userId") Long userId,
            @PathVariable("categoryName") String categoryName) {
        CategoryResponseDTO category = categoryService.getCategoryByName(userId, categoryName);
        return ResponseEntity.ok(category);
    }

}
