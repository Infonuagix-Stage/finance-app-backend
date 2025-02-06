package com.example.backend.service;

import com.example.backend.dto.ExpenseRequestDTO;
import com.example.backend.dto.ExpenseResponseDTO;
import com.example.backend.model.Category;
import com.example.backend.model.Expense;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.ExpenseRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Nouvelle méthode pour récupérer les dépenses d'un utilisateur dans une catégorie donnée
    public List<ExpenseResponseDTO> getAllExpenses(Long userId, Long categoryId) {
        return expenseRepository.findByUserIdAndCategoryId(userId, categoryId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
        return mapToResponseDTO(expense);
    }

    public ExpenseResponseDTO createExpense(ExpenseRequestDTO expenseRequestDTO) {
        User user = userRepository.findById(expenseRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + expenseRequestDTO.getUserId()));

        Category category = categoryRepository.findById(expenseRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + expenseRequestDTO.getCategoryId()));

        Expense expense = new Expense();
        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setUser(user);
        expense.setCategory(category);

        Expense savedExpense = expenseRepository.save(expense);
        return mapToResponseDTO(savedExpense);
    }

    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

        Category category = categoryRepository.findById(expenseRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + expenseRequestDTO.getCategoryId()));

        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setCategory(category);

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToResponseDTO(updatedExpense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
        expenseRepository.delete(expense);
    }

    private ExpenseResponseDTO mapToResponseDTO(Expense expense) {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setId(expense.getId());
        responseDTO.setMontant(expense.getAmount());
        responseDTO.setExpenseDate(expense.getExpenseDate());
        responseDTO.setDescription(expense.getDescription());
        responseDTO.setCategoryName(expense.getCategory().getName());
        responseDTO.setUserName(expense.getUser().getName());
        responseDTO.setCreationDate(expense.getCreationDate());
        return responseDTO;
    }


    public BigDecimal getTotalForCategory(Long userId, Long categoryId) {
        return expenseRepository.getTotalForCategory(userId, categoryId);
    }

//    public BigDecimal getTotalForUser(Long userId) {
//        return expenseRepository.getTotalForUser(userId);
//    }


}
