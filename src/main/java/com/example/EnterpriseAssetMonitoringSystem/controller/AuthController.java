package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.RegisterRequestDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.LoginRequestDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// to handle user registration and login.
@Tag(name="Authentication")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO request) {
        User user = new User(null, request.getName(), request.getEmail(), request.getPassword(), request.getRole());
        userService.register(user);
        return ResponseEntity.ok("Successfully registered");
    }


    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request) {
        userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(" Login successful");
    }
}

