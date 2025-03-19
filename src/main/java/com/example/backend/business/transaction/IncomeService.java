package com.example.backend.business.transaction;

import com.example.backend.presentation.transaction.IncomeRequestDTO;
import com.example.backend.presentation.transaction.IncomeResponseDTO;
import com.example.backend.dataaccess.category.Category;
import com.example.backend.dataaccess.transaction.Income;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.category.CategoryRepository;
import com.example.backend.dataaccess.transaction.IncomeRepository;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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

    public List<IncomeResponseDTO> getAllIncomes(String auth0UserId, UUID categoryId) {
        List<Income> incomes = incomeRepository.findByUser_Auth0UserIdAndCategory_CategoryId(auth0UserId, categoryId);
        return incomes.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public IncomeResponseDTO getIncomeById(UUID incomeId) {
        Income income = incomeRepository.findByIncomeId(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + incomeId));
        return mapToResponseDTO(income);
    }

    public IncomeResponseDTO createIncome(String auth0UserId, UUID categoryId, IncomeRequestDTO incomeRequestDTO) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + auth0UserId));

        Category category = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        Income income = new Income();
        income.setAmount(incomeRequestDTO.getAmount());
        income.setIncomeDate(incomeRequestDTO.getIncomeDate());
        income.setDescription(incomeRequestDTO.getDescription());
        income.setUser(user);
        income.setCategory(category);

        Income savedIncome = incomeRepository.save(income);
        return mapToResponseDTO(savedIncome);
    }

    public IncomeResponseDTO updateIncome(UUID id, IncomeRequestDTO incomeRequestDTO) {
        Income income = incomeRepository.findByIncomeId(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));
        Category category = categoryRepository.findByCategoryId(incomeRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + incomeRequestDTO.getCategoryId()));

        income.setAmount(incomeRequestDTO.getAmount());
        income.setIncomeDate(incomeRequestDTO.getIncomeDate());
        income.setDescription(incomeRequestDTO.getDescription());
        income.setCategory(category);

        Income updatedIncome = incomeRepository.save(income);
        return mapToResponseDTO(updatedIncome);
    }

    public void deleteIncome(UUID id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + id));
        incomeRepository.delete(income);
    }

    public BigDecimal getTotalForCategory(String auth0UserId, UUID categoryId) {
        return incomeRepository.getTotalForCategory(auth0UserId, categoryId);
    }

    private IncomeResponseDTO mapToResponseDTO(Income income) {
        IncomeResponseDTO dto = new IncomeResponseDTO();
        dto.setIncomeId(income.getIncomeId());
        dto.setAmount(income.getAmount());
        dto.setIncomeDate(income.getIncomeDate());
        dto.setDescription(income.getDescription());
        dto.setCategoryName(income.getCategory().getName());
        dto.setUserName(income.getUser().getName());
        dto.setCreationDate(income.getCreationDate());
        return dto;
    }
}
