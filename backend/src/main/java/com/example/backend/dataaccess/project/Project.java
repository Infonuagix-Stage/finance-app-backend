package com.example.backend.dataaccess.project;

import com.example.backend.dataaccess.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clé primaire auto-incrémentée

    @Column(nullable = false, unique = true, updatable = false)
    private UUID projectId; // UUID unique généré automatiquement

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Relation avec User

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double targetAmount;

    @Column(nullable = false)
    private Double savedAmount = 0.0;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private String priority;

    @Column(nullable = false)
    private Double monthlyContribution;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt; // Date de création auto-générée

    // ⚠ Génération automatique de `projectId` avant insertion
    @PrePersist
    public void generateProjectId() {
        if (this.projectId == null) {
            this.projectId = UUID.randomUUID();
        }
    }
}
