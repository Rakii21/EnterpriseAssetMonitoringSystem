package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.MaintenanceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;


@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceLogRepository maintenanceRepo;
    private final AssetRepository assetRepo;
    private final UserService userService;

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

        return maintenanceRepo.save(log);
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

        return maintenanceRepo.save(log);
    }

    public List<MaintenanceLog> getLogsByAsset(Long assetId) {
        return maintenanceRepo.findByAssetId(assetId);
    }
}
