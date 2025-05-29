package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// for user admin actions like listing users and updating user roles
@Tag(name="User")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/role")
    public User updateUserRole(@PathVariable Long id,
                               @RequestParam String role,
                               @RequestParam Long requesterId) {
        return userService.updateRoleByManager(requesterId, id, role);
    }
}