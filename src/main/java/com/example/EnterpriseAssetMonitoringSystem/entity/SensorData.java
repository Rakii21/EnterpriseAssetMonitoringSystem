package com.example.EnterpriseAssetMonitoringSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //Reference to the asset for which data was captured
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    private Double temperature;
    private Double pressure;

    private LocalDateTime timestamp; //time reading was captured


}
