package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/schedule")
    public ResponseEntity<?> schedule(@RequestBody @Valid ScheduleMaintenanceDTO dto,BindingResult bindingResult, @RequestParam Long userId) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(maintenanceService.scheduleMaintenance(dto, userId));
    }

    @PutMapping("/complete")
    public ResponseEntity<?> complete(@RequestBody @Valid CompleteMaintenanceDTO dto,BindingResult bindingResult, @RequestParam Long userId) {
        if(bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(),HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(maintenanceService.completeMaintenance(dto, userId));
    }

    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<MaintenanceLog>> getLogsByAsset(@PathVariable Long assetId) {
        return ResponseEntity.ok(maintenanceService.getLogsByAsset(assetId));
    }
}
