package com.example.backend.dataaccess.project;

import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByUser_UserId(UUID userId);

    Optional<Project> findProjectsByProjectId(UUID projectId);

    Optional<Project> findByProjectIdAndUser_UserId(UUID projectId, UUID userId);
}
