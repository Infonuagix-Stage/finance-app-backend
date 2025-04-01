package com.example.backend.dataaccess.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByUser_Auth0UserId(String auth0UserId);

    Optional<Project> findByProjectId(UUID projectId);

    Optional<Project> findByProjectIdAndUser_Auth0UserId(UUID projectId, String auth0UserId);

    void deleteByProjectId(UUID projectId);
}
