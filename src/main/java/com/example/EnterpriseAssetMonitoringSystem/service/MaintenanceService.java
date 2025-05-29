package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.MaintenanceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceLogRepository maintenanceRepo;
    private final AssetRepository assetRepo;
    private final UserService userService;
    private final UptimeLogService uptimeService;
    public MaintenanceLog scheduleMaintenance(ScheduleMaintenanceDTO dto, Long userId) {
        User user = userService.getUserById(userId);
        if (user.getRole() != Role.OPERATOR) {
            throw new RuntimeException("Only OPERATORs can schedule maintenance");
        }

        Asset asset = assetRepo.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        MaintenanceLog log = new MaintenanceLog();
        log.setAsset(asset);
        log.setScheduledDate(dto.getScheduledDate());
        log.setCompletedDate(null);
        log.setRemarks(null);

        MaintenanceLog saved =  maintenanceRepo.save(log);
        uptimeService.createDownTime(asset);
        return saved;
    }

    public MaintenanceLog completeMaintenance(CompleteMaintenanceDTO dto, Long userId) {
        User user = userService.getUserById(userId);
        if (user.getRole() != Role.OPERATOR) {
            throw new RuntimeException("Only OPERATORs can complete maintenance");
        }
        MaintenanceLog log = maintenanceRepo.findById(dto.getLogId())
                .orElseThrow(() -> new RuntimeException("Maintenance log not found"));
        log.setCompletedDate(dto.getCompletedDate());
        log.setRemarks(dto.getRemarks());
        MaintenanceLog saved = maintenanceRepo.save(log);

        // Call updateDownTime with the completed date
        uptimeService.updateDownTime(log.getAsset());
        return saved;
    }
    public List<MaintenanceLog> getLogsByAsset(Long assetId) {
        return maintenanceRepo.findByAssetId(assetId);
    }
}