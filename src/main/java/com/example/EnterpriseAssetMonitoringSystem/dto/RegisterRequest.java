package com.example.EnterpriseAssetMonitoringSystem.dto;

import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// to capture user registration details from client

@Data
public class RegisterRequest {
	
	@NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

   }


