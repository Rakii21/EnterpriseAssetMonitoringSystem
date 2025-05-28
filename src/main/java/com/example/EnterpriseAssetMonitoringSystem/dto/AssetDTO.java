package com.example.EnterpriseAssetMonitoringSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssetDTO {
    @NotBlank(message = "Asset name is required")
    private String name;

    @NotBlank(message = "Asset type is required")
    private String type;

    @NotBlank(message = "Asset location is required")
    private String location;

    private Double thresholdTemp;
    private Double thresholdPressure;

    @NotNull(message = "User ID is required")
    private Long createdByUserId;
}