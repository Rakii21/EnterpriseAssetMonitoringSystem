package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.RegisterRequest;
import com.example.EnterpriseAssetMonitoringSystem.dto.LoginRequest;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// to handle user registration and login.
@Tag(name="Auth")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        User user = new User(null, request.getName(), request.getEmail(), request.getPassword(), request.getRole());
        userService.register(user);
        return ResponseEntity.ok("Successfully registered");
    }


    @PostMapping("/login")
    public  ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        userService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(" Login successful");
    }
}

