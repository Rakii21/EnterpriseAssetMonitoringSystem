package com.example.EnterpriseAssetMonitoringSystem.dto;

import lombok.Data;

import java.time.LocalDate;


 //DTO to receive scheduling request from the frontend.

@Data
public class ScheduleMaintenanceDTO {
    private Long assetId;
    private LocalDate scheduledDate;
}
