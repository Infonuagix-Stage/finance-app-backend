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

    public List<Project> getAllProjectsByUser(UUID userId) {
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
        ProjectResponseDTO dto = new ProjectResponseDTO();

        // Copier les valeurs depuis l'entité Project
        dto.setProjectId(project.getProjectId());
        dto.setName(project.getName());
        dto.setTargetAmount(project.getTargetAmount());
        dto.setSavedAmount(project.getSavedAmount());
        dto.setDeadline(project.getDeadline());
        dto.setPriority(project.getPriority());
        dto.setMonthlyContribution(project.getMonthlyContribution());

        // Pour l'ID utilisateur, on récupère l'ID via l'objet User
        dto.setUserId(project.getUser() != null ? project.getUser().getUserId() : null);

        // Date de création
        dto.setCreatedAt(project.getCreatedAt());

        return dto;
    }
}
