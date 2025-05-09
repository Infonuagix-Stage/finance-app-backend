package com.example.backend.business.project;

import com.example.backend.presentation.project.ProjectResponseDTO;
import com.example.backend.dataaccess.project.Project;
import com.example.backend.dataaccess.project.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public Optional<Project> getProjectById(UUID projectId) {
        return projectRepository.findByProjectId(projectId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(UUID projectId) {
        projectRepository.deleteByProjectId(projectId);
    }
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findByIdAndUserId(UUID projectId, String auth0UserId) {
        return projectRepository.findByProjectIdAndUser_Auth0UserId(projectId, auth0UserId);
    }

    public ProjectResponseDTO convertToResponseDTO(Project project) {
        return null;
    }

    public void deleteByIdAndUserId(UUID projectId, String auth0UserId) {
        Optional<Project> project = projectRepository.findByProjectIdAndUser_Auth0UserId(projectId, auth0UserId);
        project.ifPresent(projectRepository::delete);
    }

    public List<Project> getAllProjectsByUser(String auth0UserId) {
        return projectRepository.findByUser_Auth0UserId(auth0UserId);
    }
}
