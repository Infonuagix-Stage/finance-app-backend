package com.example.backend.presentation.debt;

import com.example.backend.business.debt.DebtService;
import com.example.backend.dataaccess.debt.Debt;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{auth0UserId:.+}/debts")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<DebtResponseDTO>> getDebtsByUser(@PathVariable String auth0UserId) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Debt> debts = debtService.getAllDebtsByUser(user.getAuth0UserId());
        return ResponseEntity.ok(debtService.convertToResponseDTOList(debts));
    }

    @GetMapping("/{debtId}")
    public ResponseEntity<DebtResponseDTO> getDebtById(
            @PathVariable UUID debtId,
            @PathVariable String auth0UserId) {

        Optional<Debt> optionalDebt = debtService.findByIdAndUserId(debtId, auth0UserId);

        if (optionalDebt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        DebtResponseDTO dto = debtService.convertToResponseDTO(optionalDebt.get());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<DebtResponseDTO> createDebt(
            @PathVariable String auth0UserId,
            @RequestBody DebtRequestDTO debtRequestDTO) {

        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Debt debt = new Debt();
        debt.setUser(user);
        debt.setCreditor(debtRequestDTO.getCreditor());
        debt.setAmountOwed(debtRequestDTO.getAmountOwed());
        debt.setAmountPaid(debtRequestDTO.getAmountPaid());
        debt.setDueDate(debtRequestDTO.getDueDate());
        debt.setMonthlyPayment(debtRequestDTO.getMonthlyPayment());
        debt.setStatus(debtRequestDTO.getStatus());

        Debt savedDebt = debtService.save(debt);
        DebtResponseDTO responseDTO = debtService.convertToResponseDTO(savedDebt);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{debtId}")
    public ResponseEntity<DebtResponseDTO> updateDebt(
            @PathVariable String auth0UserId,
            @PathVariable UUID debtId,
            @RequestBody DebtRequestDTO debtRequestDTO) {

        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Debt> optionalDebt = debtService.findByIdAndUserId(debtId, user.getAuth0UserId());
        if (optionalDebt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Debt debt = optionalDebt.get();
        debt.setCreditor(debtRequestDTO.getCreditor());
        debt.setAmountOwed(debtRequestDTO.getAmountOwed());
        debt.setAmountPaid(debtRequestDTO.getAmountPaid());
        debt.setDueDate(debtRequestDTO.getDueDate());
        debt.setMonthlyPayment(debtRequestDTO.getMonthlyPayment());
        debt.setStatus(debtRequestDTO.getStatus());

        Debt updatedDebt = debtService.save(debt);
        DebtResponseDTO responseDTO = debtService.convertToResponseDTO(updatedDebt);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{debtId}")
    public ResponseEntity<Void> deleteDebt(
            @PathVariable String auth0UserId,
            @PathVariable UUID debtId) {

        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        debtService.deleteByIdAndUserId(debtId, user.getAuth0UserId());
        return ResponseEntity.noContent().build();
    }
}
