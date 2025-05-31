package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.exception.UnauthorizedException;
import com.example.EnterpriseAssetMonitoringSystem.exception.UserInvalidException;
import com.example.EnterpriseAssetMonitoringSystem.exception.UserNotFoundException;
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
            throw new UserInvalidException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    // Validates login by comparing email and password
    public User login(String email, String password) {
        return userRepo.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new UserInvalidException("Invalid email or password"));
    }

    // Returns list of all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Updates a user’s role
    public User updateRoleByManager(Long requesterId, Long userId, String role) {
        User requester = getUserById(requesterId);
        if (requester.getRole() != Role.MANAGER) {
            throw new UnauthorizedException("Only MANAGERs can update roles");
        }
        User user = getUserById(userId);
        user.setRole(Role.valueOf(role.toUpperCase()));
        return userRepo.save(user);
    }

    // Fetch user info
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
