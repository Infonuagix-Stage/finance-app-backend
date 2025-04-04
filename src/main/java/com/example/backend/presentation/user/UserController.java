package com.example.backend.presentation.user;

import com.example.backend.dataaccess.user.User;
import com.example.backend.business.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers()
                .stream()
                .map(userService::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String auth0UserId) {
        User user = userService.getUserByAuth0UserId(auth0UserId);
        return ResponseEntity.ok(userService.toResponseDTO(user));
    }

    // Create a new user
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        User createdUser = userService.createUser(userService.toEntity(userRequestDTO));
        return ResponseEntity.ok(userService.toResponseDTO(createdUser));
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable String auth0UserId,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        User updatedUser = userService.updateUser(auth0UserId, userService.toEntity(userRequestDTO));
        return ResponseEntity.ok(userService.toResponseDTO(updatedUser));
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String auth0UserId) {
        userService.deleteUser(auth0UserId);
        return ResponseEntity.noContent().build();
    }
}
