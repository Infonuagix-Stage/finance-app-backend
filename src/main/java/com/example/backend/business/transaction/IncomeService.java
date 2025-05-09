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

    public List<IncomeResponseDTO> getMonthlyIncomes(String auth0UserId, int year, int month, UUID categoryId) {
        List<Income> incomes;

        if (categoryId != null) {
            incomes = incomeRepository.findByUserYearMonthAndCategory(auth0UserId, year, month, categoryId);
        } else {
            incomes = incomeRepository.findByUserYearAndMonth(auth0UserId, year, month);
        }

        return incomes.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    public List<IncomeResponseDTO> getAllIncomes(String auth0UserId, UUID categoryId) {
        List<Income> incomes = incomeRepository.findByUser_Auth0UserIdAndCategory_CategoryId(auth0UserId, categoryId);
        return incomes.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public IncomeResponseDTO getIncomeByIncomeId(String auth0UserId, UUID categoryId, UUID incomeId) {
        Income income = incomeRepository.findByIncomeId(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found for incomeId: " + incomeId));
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

    public IncomeResponseDTO updateIncome(String auth0UserId, UUID categoryId, UUID incomeId, IncomeRequestDTO incomeRequestDTO) {
        Income income = incomeRepository.findByIncomeId(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + incomeId));
        Category category = categoryRepository.findByCategoryId(incomeRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + incomeRequestDTO.getCategoryId()));

        income.setAmount(incomeRequestDTO.getAmount());
        income.setIncomeDate(incomeRequestDTO.getIncomeDate());
        income.setDescription(incomeRequestDTO.getDescription());
        income.setCategory(category);

        Income updatedIncome = incomeRepository.save(income);
        return mapToResponseDTO(updatedIncome);
    }

    public void deleteIncome(UUID incomeId) {
        Income income = incomeRepository.findByIncomeId(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found with id " + incomeId));
        incomeRepository.delete(income);
    }

    public BigDecimal getTotalForCategory(String auth0UserId, UUID categoryId, Integer year, Integer month) {
        if (year == null || month == null) {
            return incomeRepository.getTotalForCategory(auth0UserId, categoryId);
        } else {
            return incomeRepository.getTotalForCategoryYearMonth(auth0UserId, categoryId, year, month);
        }
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
