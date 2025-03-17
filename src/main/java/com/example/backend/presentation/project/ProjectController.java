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
@RequestMapping("/api/v1/users/{auth0UserId:.+}/projects")
public class ProjectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable String auth0UserId) {
        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(auth0UserId);
        List<Project> projects = projectService.getAllProjectsByUser(user.getUserId());
        return ResponseEntity.ok(projects);

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID projectId) {
        Optional<Project> project = projectService.getProjectById(projectId);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> createProject(
            @PathVariable String auth0UserId,
            @RequestBody ProjectRequestDTO projectRequestDTO) {

        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(projectRequestDTO.getName());
        project.setTargetAmount(projectRequestDTO.getTargetAmount());
        project.setSavedAmount(projectRequestDTO.getSavedAmount());
        project.setDeadline(projectRequestDTO.getDeadline());
        project.setPriority(projectRequestDTO.getPriority());
        project.setMonthlyContribution(projectRequestDTO.getMonthlyContribution());
        project.setUser(user);

        Project savedProject = projectService.save(project);
        ProjectResponseDTO responseDTO = convertToResponseDTO(savedProject);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable String auth0UserId,
            @PathVariable UUID projectId,
            @RequestBody ProjectRequestDTO projectRequestDTO) {

        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Project> projectOptional = projectService.findByIdAndUserId(projectId, user.getUserId());
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
        ProjectResponseDTO responseDTO = convertToResponseDTO(updatedProject);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable String auth0UserId,
            @PathVariable UUID projectId) {

        User user = userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        projectService.deleteByIdAndUserId(projectId, user.getUserId());

        return ResponseEntity.noContent().build();
    }

    private ProjectResponseDTO convertToResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setProjectId(project.getProjectId());
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
}
