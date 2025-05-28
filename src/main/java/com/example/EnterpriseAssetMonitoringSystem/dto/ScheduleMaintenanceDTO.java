package com.example.EnterpriseAssetMonitoringSystem.dto;

import lombok.Data;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;


@Data
public class ScheduleMaintenanceDTO {
    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Scheduled date is required")
    @FutureOrPresent(message = "Scheduled date cannot be in the past")
    private LocalDate scheduledDate;
}
