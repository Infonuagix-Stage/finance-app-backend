package com.example.backend.service;

import com.example.backend.model.Expense;
import com.example.backend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Récupérer toutes les dépenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Récupérer une dépense par ID
    public Expense getExpenseById(Integer id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    // Créer une nouvelle dépense
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Mettre à jour une dépense
    public Expense updateExpense(Integer id, Expense expenseDetails) {
        Expense expense = getExpenseById(id);
        expense.setMontant(expenseDetails.getMontant());
        expense.setExpenseDate(expenseDetails.getExpenseDate());
        expense.setDescription(expenseDetails.getDescription());
        expense.setCategorie(expenseDetails.getCategorie());
        expense.setUser(expenseDetails.getUser());
        return expenseRepository.save(expense);
    }

    // Supprimer une dépense
    public void deleteExpense(Integer id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }
}
