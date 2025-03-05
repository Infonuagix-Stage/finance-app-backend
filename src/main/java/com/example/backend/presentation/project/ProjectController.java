package com.example.backend.presentation.project;

import com.example.backend.dataaccess.project.Project;
import com.example.backend.business.project.ProjectService;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/projects")
public class ProjectController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping()
    public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable UUID userId) {
        System.out.println("Controller getProjectsByUser");
        return ResponseEntity.ok(projectService.getAllProjectsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @PathVariable UUID userID,
            @RequestBody ProjectRequestDTO projectRequestDTO) {

        // 1. Récupérer l'utilisateur via son ID
        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));

        // 2. Créer l'entité Project à partir du DTO
        Project project = new Project();
        project.setName(projectRequestDTO.getName());
        project.setTargetAmount(projectRequestDTO.getTargetAmount());
        project.setSavedAmount(projectRequestDTO.getSavedAmount());
        project.setDeadline(projectRequestDTO.getDeadline());
        project.setPriority(projectRequestDTO.getPriority());
        project.setMonthlyContribution(projectRequestDTO.getMonthlyContribution());

        // 3. Assigner l’objet user récupéré
        project.setUser(user);

        // 4. Sauvegarder le nouveau projet
        Project savedProject = projectService.save(project);

        // 5. Convertir l’entité sauvegardée en DTO
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
        dto.setUserId(project.getUser().getUserId());
        dto.setCreatedAt(project.getCreatedAt());
        return dto;
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable UUID userId,
            @PathVariable UUID id,
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
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
