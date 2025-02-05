package com.example.backend.service;

import com.example.backend.dto.IncomeRequestDTO;
import com.example.backend.dto.IncomeResponseDTO;
import com.example.backend.model.Category;
import com.example.backend.model.Income;
import com.example.backend.model.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.IncomeRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // Récupère toutes les incomes pour un utilisateur dans une catégorie donnée
    public List<IncomeResponseDTO> getAllIncomes(Long userId, Long categoryId) {
        List<Income> incomes = incomeRepository.findByUserIdAndCategoryId(userId, categoryId);
        return incomes.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Récupère une income par son ID
    public IncomeResponseDTO getIncomeById(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));
        return mapToResponseDTO(income);
    }

    // Crée une income à partir du DTO de requête
    public IncomeResponseDTO createIncome(IncomeRequestDTO incomeRequestDTO) {
        User user = userRepository.findById(incomeRequestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + incomeRequestDTO.getUserId()));
        Category category = categoryRepository.findById(incomeRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + incomeRequestDTO.getCategoryId()));

        Income income = new Income();
        income.setAmount(incomeRequestDTO.getAmount());
        income.setIncomeDate(incomeRequestDTO.getIncomeDate());
        income.setDescription(incomeRequestDTO.getDescription());
        income.setUser(user);
        income.setCategory(category);

        Income savedIncome = incomeRepository.save(income);
        return mapToResponseDTO(savedIncome);
    }

    // Met à jour une income existante
    public IncomeResponseDTO updateIncome(Long id, IncomeRequestDTO incomeRequestDTO) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));
        Category category = categoryRepository.findById(incomeRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + incomeRequestDTO.getCategoryId()));

        income.setAmount(incomeRequestDTO.getAmount());
        income.setIncomeDate(incomeRequestDTO.getIncomeDate());
        income.setDescription(incomeRequestDTO.getDescription());
        income.setCategory(category);

        Income updatedIncome = incomeRepository.save(income);
        return mapToResponseDTO(updatedIncome);
    }

    // Supprime une income par ID
    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));
        incomeRepository.delete(income);
    }

    // Calcule le total des incomes pour une catégorie donnée d'un utilisateur
    public BigDecimal getTotalForCategory(Long userId, Long categoryId) {
        return incomeRepository.getTotalForCategory(userId, categoryId);
    }

    // Méthode privée pour mapper une entité Income en IncomeResponseDTO
    private IncomeResponseDTO mapToResponseDTO(Income income) {
        IncomeResponseDTO dto = new IncomeResponseDTO();
        dto.setId(income.getId());
        dto.setAmount(income.getAmount());
        dto.setIncomeDate(income.getIncomeDate());
        dto.setDescription(income.getDescription());
        dto.setCategoryName(income.getCategory().getName());
        dto.setUserName(income.getUser().getName());
        dto.setCreationDate(income.getCreationDate());
        return dto;
    }
}
