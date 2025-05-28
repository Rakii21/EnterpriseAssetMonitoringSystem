package com.example.EnterpriseAssetMonitoringSystem.dto;

import lombok.Data;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Data
public class CompleteMaintenanceDTO {
    @NotNull(message = "Log ID is required")
    private Long logId;

    @NotNull(message = "Completed date is required")
    private LocalDate completedDate;

    @NotBlank(message = "Remarks are required")
    private String remarks;
}
