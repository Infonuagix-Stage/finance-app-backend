package com.example.backend.dataaccess.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);  // Get all projects for a specific user

    Optional<Project> findByIdAndUserId(Long projectId, Long userId);
}
