package com.example.EnterpriseAssetMonitoringSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String location;

    private Double thresholdTemp;
    private Double thresholdPressure;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
}
