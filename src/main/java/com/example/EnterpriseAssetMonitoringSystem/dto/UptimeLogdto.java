package com.example.EnterpriseAssetMonitoringSystem.dto;

import jakarta.transaction.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//DTP to receive uptime log entries
import java.time.LocalTime;

@Data
public class UptimeLogdto {
    @NotNull(message = "A sset ID is required")
    private Long assetId;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Status is required")
    private Status status;
}
