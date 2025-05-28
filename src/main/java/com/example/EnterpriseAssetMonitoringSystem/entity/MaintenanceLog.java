package com.example.EnterpriseAssetMonitoringSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a scheduled or completed maintenance event
 * for a specific asset.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the asset
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private LocalDate scheduledDate;    // When maintenance was scheduled
    private LocalDate completedDate;    // When it was actually completed
    private String remarks;             // Optional description of maintenance performed
}
