package com.example.EnterpriseAssetMonitoringSystem.entity;

import jakarta.persistence.*;
import lombok.*;

// Represents a system user (employee) with basic info and role.
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // MANAGER or OPERATOR
}