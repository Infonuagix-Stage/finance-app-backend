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

    public List<Project> getAllProjectsByUserId(UUID userId) {
        return projectRepository.findByUser_UserId(userId);
    }

    public Optional<Project> getProjectById(UUID projectId) {
        return projectRepository.findProjectsByProjectId(projectId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(UUID projectId) {
        projectRepository.deleteById(projectId);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findByIdAndUserId(UUID projectId, UUID userId) {
        return projectRepository.findByProjectIdAndUser_UserId(projectId, userId);
    }

    public ProjectResponseDTO convertToResponseDTO(Project project) {
        return null;
    }

    public void deleteByIdAndUserId(UUID projectId, UUID userId) {
    }


}
