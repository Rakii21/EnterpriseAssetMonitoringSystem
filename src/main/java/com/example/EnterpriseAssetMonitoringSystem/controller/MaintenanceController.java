package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/schedule")
    public MaintenanceLog schedule(@RequestBody @Valid ScheduleMaintenanceDTO dto, @RequestParam Long userId) {
        return maintenanceService.scheduleMaintenance(dto, userId);
    }

    @PutMapping("/complete")
    public MaintenanceLog complete(@RequestBody @Valid CompleteMaintenanceDTO dto, @RequestParam Long userId) {
        return maintenanceService.completeMaintenance(dto, userId);
    }

    @GetMapping("/asset/{assetId}")
    public List<MaintenanceLog> getLogsByAsset(@PathVariable Long assetId) {
        return maintenanceService.getLogsByAsset(assetId);
    }
}
