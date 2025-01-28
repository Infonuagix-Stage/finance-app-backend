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

    public List<ExpenseResponseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public ExpenseResponseDTO getExpenseById(Integer id) {
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
        expense.setMontant(expenseRequestDTO.getMontant());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setUser(user);
        expense.setCategorie(category);

        Expense savedExpense = expenseRepository.save(expense);
        return mapToResponseDTO(savedExpense);
    }

    public ExpenseResponseDTO updateExpense(Integer id, ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));

        Category category = categoryRepository.findById(expenseRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + expenseRequestDTO.getCategoryId()));

        expense.setMontant(expenseRequestDTO.getMontant());
        expense.setExpenseDate(expenseRequestDTO.getExpenseDate());
        expense.setDescription(expenseRequestDTO.getDescription());
        expense.setCategorie(category);

        Expense updatedExpense = expenseRepository.save(expense);
        return mapToResponseDTO(updatedExpense);
    }

    public void deleteExpense(Integer id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
        expenseRepository.delete(expense);
    }

    private ExpenseResponseDTO mapToResponseDTO(Expense expense) {
        ExpenseResponseDTO responseDTO = new ExpenseResponseDTO();
        responseDTO.setId(expense.getId());
        responseDTO.setMontant(expense.getMontant());
        responseDTO.setExpenseDate(expense.getExpenseDate());
        responseDTO.setDescription(expense.getDescription());
        responseDTO.setCategoryName(expense.getCategorie().getName());
        responseDTO.setUserName(expense.getUser().getName());
        responseDTO.setCreationDate(expense.getCreationDate());
        return responseDTO;
    }
}
