package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    //Schedule maintenance
    @PostMapping("/schedule")
    public MaintenanceLog schedule(@RequestBody ScheduleMaintenanceDTO dto) {
        return maintenanceService.scheduleMaintenance(dto);
    }

    //Complete a maintenance event
    @PutMapping("/complete")
    public MaintenanceLog complete(@RequestBody CompleteMaintenanceDTO dto) {
        return maintenanceService.completeMaintenance(dto);
    }

    //Get all logs for an asset
    @GetMapping("/asset/{assetId}")
    public List<MaintenanceLog> getLogsByAsset(@PathVariable Long assetId) {
        return maintenanceService.getLogsByAsset(assetId);
    }
}
