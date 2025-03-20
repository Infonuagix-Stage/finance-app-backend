package com.example.backend.business.transaction;

import com.example.backend.presentation.transaction.ExpenseRequestDTO;
import com.example.backend.presentation.transaction.ExpenseResponseDTO;
import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.transaction.Expense;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.category.CategoryRepository;
import com.example.backend.dataaccess.transaction.ExpenseRepository;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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
    public List<ExpenseResponseDTO> getAllExpenses(String auth0UserId, UUID categoryId) {
        return expenseRepository.findByUser_Auth0UserIdAndCategory_CategoryId(auth0UserId, categoryId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ExpenseResponseDTO getExpenseById(UUID expenseId) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + expenseId));
        return mapToResponseDTO(expense);
    }

    public ExpenseResponseDTO createExpense(String auth0UserId, UUID categoryId , ExpenseRequestDTO expenseRequestDTO) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + expenseRequestDTO.getAuth0UserId()));

        Category category = categoryRepository.findByCategoryId(categoryId)
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
    public ExpenseResponseDTO updateExpense(String auth0UserId, UUID categoryId, UUID expenseId, ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found for expenseId: " + expenseId));

        Category category = categoryRepository.findByCategoryId(expenseRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + expenseRequestDTO.getCategoryId()));

        expense.setAmount(expenseRequestDTO.getAmount());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setCategory(category);

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToResponseDTO(updatedExpense);
    }

    public void deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + expenseId));
        expenseRepository.delete(expense);
    }

    private ExpenseResponseDTO mapToResponseDTO(Expense expense) {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setExpenseId(expense.getExpenseId());
        responseDTO.setAmount(expense.getAmount());
        responseDTO.setExpenseDate(expense.getExpenseDate());
        responseDTO.setDescription(expense.getDescription());
        responseDTO.setCategoryName(expense.getCategory().getName());
        responseDTO.setUserName(expense.getUser().getName());
        responseDTO.setCreationDate(expense.getCreationDate());
        return responseDTO;
    }


    public BigDecimal getTotalForCategory(String auth0UserId, UUID categoryId) {
        return expenseRepository.getTotalForCategory(auth0UserId, categoryId);
    }

    public ExpenseResponseDTO getExpenseByExpenseId(String auth0UserId, UUID categoryId, UUID expenseId) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found for expenseId: " + expenseId));
        return new ExpenseResponseDTO();
    }
}
