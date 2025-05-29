package com.example.EnterpriseAssetMonitoringSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * UptimeLog entity records when an asset is operational (UP) or non-operational (DOWN).
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UptimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the asset being monitored
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private LocalDateTime startTime;  // Start of uptime/downtime period
    private LocalDateTime endTime;    // End of uptime/downtime period

    @Enumerated(EnumType.STRING)
    private Status status; // UP or DOWN

    // Enum to define status type
    public enum Status {
        UP,
        DOWN
}
}