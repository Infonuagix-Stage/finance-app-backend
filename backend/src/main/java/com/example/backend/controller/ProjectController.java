package com.example.backend.controller;

import com.example.backend.dto.ProjectRequestDTO;
import com.example.backend.dto.ProjectResponseDTO;
import com.example.backend.model.Project;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.model.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/{userId}/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable Long userId) {
        System.out.println("Controller getProjectsByUser");
        return ResponseEntity.ok(projectService.getAllProjectsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @PathVariable Long userId,
            @RequestBody ProjectRequestDTO projectRequestDTO) {

        // Create a new Project entity from the incoming DTO
        Project project = new Project();
        project.setName(projectRequestDTO.getName());
        project.setTargetAmount(projectRequestDTO.getTargetAmount());
        project.setSavedAmount(projectRequestDTO.getSavedAmount());
        project.setDeadline(projectRequestDTO.getDeadline());
        project.setPriority(projectRequestDTO.getPriority());
        project.setMonthlyContribution(projectRequestDTO.getMonthlyContribution());

        project.setUserId(userId);
        Project savedProject = projectService.save(project);

        ProjectResponseDTO responseDTO = convertToResponseDTO(savedProject);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Example conversion method
    private ProjectResponseDTO convertToResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setTargetAmount(project.getTargetAmount());
        dto.setSavedAmount(project.getSavedAmount());
        dto.setDeadline(project.getDeadline());
        dto.setPriority(project.getPriority());
        dto.setMonthlyContribution(project.getMonthlyContribution());
        dto.setUserId(project.getUserId());
        dto.setCreatedAt(project.getCreatedAt());
        return dto;
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody ProjectRequestDTO projectRequestDTO) {

        Optional<Project> projectOptional = projectService.findByIdAndUserId(id, userId);
        if (projectOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Project project = projectOptional.get();
        project.setName(projectRequestDTO.getName());
        project.setTargetAmount(projectRequestDTO.getTargetAmount());
        project.setSavedAmount(projectRequestDTO.getSavedAmount());
        project.setDeadline(projectRequestDTO.getDeadline());
        project.setPriority(projectRequestDTO.getPriority());
        project.setMonthlyContribution(projectRequestDTO.getMonthlyContribution());

        Project updatedProject = projectService.save(project);

        ProjectResponseDTO responseDTO = projectService.convertToResponseDTO(updatedProject);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
