package com.example.EnterpriseAssetMonitoringSystem.dto;

import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UptimeLogDTO {
    @NotNull(message = "Asset ID is required")
    private Long assetId;

    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;

    @NotNull(message = "Status is required")
    private Status status;
}