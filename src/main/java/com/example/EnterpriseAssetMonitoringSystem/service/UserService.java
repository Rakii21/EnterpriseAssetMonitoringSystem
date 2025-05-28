package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    // Registers a new user
    public User register(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    // Validates login by comparing email and password
    public User login(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    // Returns list of all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Updates a user’s role
    public User updateRole(Long userId, String role) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Enum.valueOf(com.example.EnterpriseAssetMonitoringSystem.entity.Role.class, role.toUpperCase()));
        return userRepo.save(user);
    }

    public User updateRoleByManager(Long requesterId, Long userId, String role) {
        User requester = getUserById(requesterId);
        if (requester.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only MANAGERS can update roles");
        }
        return updateRole(userId, role);
    }
    
    // Fetch role info
    public User getUserById(Long id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    // Check role
    public void validateUserRole(Long userId, Role expectedRole) {
        User user = getUserById(userId);
        if (user.getRole() != expectedRole) {
            throw new RuntimeException("Access denied for role: " + user.getRole());
        }
    }
}
