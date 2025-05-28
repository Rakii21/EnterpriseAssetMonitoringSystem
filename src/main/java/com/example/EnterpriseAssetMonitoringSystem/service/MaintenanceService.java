package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.repository.MaintenanceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Business logic for handling maintenance operations.
 */
@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceLogRepository maintenanceRepo;
    private final AssetRepository assetRepo;

    // Schedule a new maintenance
    public MaintenanceLog scheduleMaintenance(ScheduleMaintenanceDTO dto) {
        Asset asset = assetRepo.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        MaintenanceLog log = new MaintenanceLog();
        log.setAsset(asset);
        log.setScheduledDate(dto.getScheduledDate());
        log.setCompletedDate(null);   // Not completed yet
        log.setRemarks(null);         // No remarks yet

        return maintenanceRepo.save(log);
    }

    // Mark maintenance as complete
    public MaintenanceLog completeMaintenance(CompleteMaintenanceDTO dto) {
        MaintenanceLog log = maintenanceRepo.findById(dto.getLogId())
                .orElseThrow(() -> new RuntimeException("Maintenance log not found"));

        log.setCompletedDate(dto.getCompletedDate());
        log.setRemarks(dto.getRemarks());

        return maintenanceRepo.save(log);
    }

    // Get all maintenance logs for an asset
    public List<MaintenanceLog> getLogsByAsset(Long assetId) {
        return maintenanceRepo.findByAssetId(assetId);
    }
}
