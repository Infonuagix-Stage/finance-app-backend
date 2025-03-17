package com.example.backend.presentation.category;

import com.example.backend.business.category.CategoryService;
import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.category.CategoryType;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{auth0UserId:.+}/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupérer toutes les catégories d'un utilisateur donné (via auth0UserId).
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(
            @PathVariable String auth0UserId
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));
        List<CategoryResponseDTO> categories = categoryService.getAllCategories(user.getUserId());

        return ResponseEntity.ok(categories);
    }

    /**
     * Récupérer les catégories de type EXPENSE d'un utilisateur.
     */
    @GetMapping("/expense")
    public ResponseEntity<List<CategoryResponseDTO>> getExpenseCategories(
            @PathVariable String auth0UserId
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        List<CategoryResponseDTO> expenses = categoryService.getExpenseCategories(user.getUserId());
        return ResponseEntity.ok(expenses);
    }

    /**
     * Récupérer les catégories de type INCOME d'un utilisateur.
     */
    @GetMapping("/income")
    public ResponseEntity<List<CategoryResponseDTO>> getIncomeCategories(
            @PathVariable String auth0UserId
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        List<CategoryResponseDTO> incomes = categoryService.getIncomeCategories(user.getUserId());
        return ResponseEntity.ok(incomes);
    }

    /**
     * Récupérer une catégorie spécifique par son ID (propre à la base) pour l'utilisateur donné.
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable String auth0UserId,
            @PathVariable("categoryId") String categoryId
    ) {
        // Récupération de l'utilisateur
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        // Conversion de l'ID (si c'est un UUID en base)
        // NB: adaptez selon votre besoin si categoryId est un UUID
        java.util.UUID catId = java.util.UUID.fromString(categoryId);

        // Appel service
        CategoryResponseDTO categoryDTO = categoryService.getCategoryByIdForUser(user.getUserId(), catId);
        return ResponseEntity.ok(categoryDTO);
    }

    /**
     * Création d'une catégorie (type quelconque) pour l'utilisateur.
     */
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @PathVariable String auth0UserId,
            @RequestBody CategoryRequestDTO requestDTO
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        // Conversion du DTO -> Entité Category
        Category category = categoryService.convertToEntity(requestDTO, user);
        Category savedCategory = categoryService.saveCategory(category);

        // Conversion entité -> DTO
        CategoryResponseDTO responseDTO = categoryService.convertToDTO(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    /**
     * Création d'une catégorie de type EXPENSE pour l'utilisateur.
     */
    @PostMapping("/expense")
    public ResponseEntity<CategoryResponseDTO> createExpenseCategory(
            @PathVariable String auth0UserId,
            @RequestBody CategoryRequestDTO requestDTO
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        // Forcer le type
        requestDTO.setType(CategoryType.EXPENSE.name());

        Category category = categoryService.convertToEntity(requestDTO, user);
        Category savedCategory = categoryService.saveCategory(category);

        CategoryResponseDTO responseDTO = categoryService.convertToDTO(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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

    /**
     * Mise à jour d'une catégorie pour l'utilisateur (à partir de son ID).
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable String auth0UserId,
            @PathVariable("categoryId") String categoryId,
            @RequestBody Category categoryDetails
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        java.util.UUID catId = java.util.UUID.fromString(categoryId);

        CategoryResponseDTO updatedCategory =
                categoryService.updateCategoryForUser(user.getUserId(), catId, categoryDetails);

        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Suppression d'une catégorie pour l'utilisateur (à partir de son ID).
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable String auth0UserId,
            @PathVariable("categoryId") String categoryId
    ) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with auth0UserId: " + auth0UserId));

        java.util.UUID catId = java.util.UUID.fromString(categoryId);

        categoryService.deleteCategoryForUser(user.getUserId(), catId);

        return ResponseEntity.noContent().build();
    }
}
