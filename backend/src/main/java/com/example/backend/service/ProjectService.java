package com.example.backend.service;

import com.example.backend.dto.ProjectResponseDTO;
import com.example.backend.model.Project;
import com.example.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjectsByUser(Long userId) {
        return projectRepository.findByUserId(userId);
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findByIdAndUserId(Long projectId, Long userId) {
        return projectRepository.findByIdAndUserId(projectId, userId);
    }

    public ProjectResponseDTO convertToResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();

        // Copier les valeurs depuis l'entité Project
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setTargetAmount(project.getTargetAmount());
        dto.setSavedAmount(project.getSavedAmount());

        // Pour la date d'échéance, vous pouvez la formater comme vous le souhaitez
        dto.setDeadline(project.getDeadline() != null ? project.getDeadline() : null);

        dto.setPriority(project.getPriority());
        dto.setMonthlyContribution(project.getMonthlyContribution());

        // Comme nous utilisons un Long pour stocker l'ID utilisateur
        dto.setUserId(project.getUserId());

        // Formater la date de création (createdAt) en chaîne, ou utiliser un DateTimeFormatter pour un format personnalisé
        dto.setCreatedAt(project.getCreatedAt() != null ? project.getCreatedAt() : null);

        return dto;
    }
}