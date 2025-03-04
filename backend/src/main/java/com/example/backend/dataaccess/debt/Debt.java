package com.example.backend.dataaccess.debt;

import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID debtId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Remplace `userId` par une relation avec `User`

    @Column(nullable = false)
    private String creditor;

    @Column(nullable = false)
    private Double amountOwed;

    @Column(nullable = false)
    private Double amountPaid = 0.0;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private Double monthlyPayment;

    @Column(nullable = false)
    private String status = "Pending";

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getter pour userId
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }

    // Setter pour userId
    public void setUserId(Long userId) {
        if (this.user == null) {
            this.user = new User();
        }
        this.user.setId(userId);
    }
}
