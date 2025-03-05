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

    // GET : Récupérer toutes les catégories pour un utilisateur
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(@PathVariable("userId") UUID userId) {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories(userId);
        return ResponseEntity.ok(categories);
    }

    // GET : Récupérer les catégories expense
    @GetMapping("/expense")
    public ResponseEntity<List<CategoryResponseDTO>> getExpensesCategories(
            @PathVariable("userId") UUID userId) {
        List<CategoryResponseDTO> expenses = categoryService.getExpenseCategories(userId);
        return ResponseEntity.ok(expenses);
    }

    // GET : Récupérer les catégories expense
    @GetMapping("/income")
    public ResponseEntity<List<CategoryResponseDTO>> getIncomesCategories(
            @PathVariable("userId") UUID userId){
        List<CategoryResponseDTO> incomes = categoryService.getIncomeCategories(userId);
        return ResponseEntity.ok(incomes);
    }


    // GET : Récupérer une catégorie par ID pour un utilisateur
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable UUID userId, @PathVariable UUID id) {
        CategoryResponseDTO category = categoryService.getCategoryByIdForUser(userId, id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @PathVariable UUID userId,
            @RequestBody CategoryRequestDTO requestDTO) {

        // Vérifier l'existence de l'utilisateur
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Convertir le DTO en entité et sauvegarder
        Category category = categoryService.convertToEntity(requestDTO, user);
        Category savedCategory = categoryService.saveCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.convertToDTO(savedCategory));
    }

    // 🔹 POST : Créer une catégorie de type EXPENSE
    @PostMapping("/expense")
    public ResponseEntity<CategoryResponseDTO> createExpenseCategory(
            @PathVariable UUID userId,
            @RequestBody CategoryRequestDTO requestDTO) {

        // Vérifier l'existence de l'utilisateur
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Forcer le type à EXPENSE
        requestDTO.setType(CategoryType.EXPENSE.name());

        // Convertir le DTO en entité et sauvegarder
        Category category = categoryService.convertToEntity(requestDTO, user);
        Category savedCategory = categoryService.saveCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.convertToDTO(savedCategory));
    }

    // 🔹 POST : Créer une catégorie de type INCOME
    @PostMapping("/income")
    public ResponseEntity<CategoryResponseDTO> createIncomeCategory(
            @PathVariable UUID userId,
            @RequestBody CategoryRequestDTO requestDTO) {

        // Vérifier l'existence de l'utilisateur
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Forcer le type à INCOME
        requestDTO.setType(CategoryType.INCOME.name());

        // Convertir le DTO en entité et sauvegarder
        Category category = categoryService.convertToEntity(requestDTO, user);
        Category savedCategory = categoryService.saveCategory(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.convertToDTO(savedCategory));
    }

    // PUT : Mise à jour d'une catégorie par ID pour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable UUID userId,
            @PathVariable UUID id,
            @RequestBody Category categoryDetails
    ) {
        CategoryResponseDTO updatedCategory = categoryService.updateCategoryForUser(userId, id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // DELETE : Suppression d'une catégorie par ID pour un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID userId, @PathVariable UUID id) {
        categoryService.deleteCategoryForUser(userId, id);
        return ResponseEntity.noContent().build();
    }

//    // (Optionnel) GET : Récupérer une catégorie par nom
//    @GetMapping("/name/{categoryName}")
//    public ResponseEntity<CategoryResponseDTO> getCategoryByName(
//            @PathVariable("userId") UUID userId,
//            @PathVariable("categoryName") String categoryName) {
//        CategoryResponseDTO category = categoryService.getCategoryByName(userId, categoryName);
//        return ResponseEntity.ok(category);
//    }

}
