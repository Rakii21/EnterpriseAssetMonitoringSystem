package com.example.EnterpriseAssetMonitoringSystem.dto;

import lombok.Data;

import java.time.LocalDate;


 //DTO for marking a maintenance log as completed.
@Data
public class CompleteMaintenanceDTO {
    private Long logId;
    private LocalDate completedDate;
    private String remarks;
}
