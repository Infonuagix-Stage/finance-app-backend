package com.example.backend.business.user;

import com.example.backend.presentation.user.UserRequestDTO;
import com.example.backend.presentation.user.UserResponseDTO;
import com.example.backend.dataaccess.user.User;
import com.example.backend.dataaccess.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByAuth0UserId(String auth0UserId) {
        return userRepository.findByAuth0UserId(auth0UserId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + auth0UserId));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String auth0UserId, User userDetails) {
        User user = getUserByAuth0UserId(auth0UserId);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(String auth0UserId) {
        User user = getUserByAuth0UserId(auth0UserId);
        userRepository.delete(user);
    }

    // Convert UserRequestDTO to User entity
    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    // Convert User entity to UserResponseDTO
    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getAuth0UserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
    // UserService.java
    public User getOrCreateUserFromAuth0(String auth0UserId, String email, String name) {
        Optional<User> existingUser = userRepository.findByAuth0UserId(auth0UserId);

        if(existingUser.isPresent()) {
            return existingUser.get();
        }

        User newUser = new User();
        newUser.setAuth0UserId(auth0UserId);
        newUser.setEmail(email);
        newUser.setName(name);
        return userRepository.save(newUser);
    }
}
