package com.example.EnterpriseAssetMonitoringSystem.repository;


import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaintenanceLogRepository extends JpaRepository<MaintenanceLog, Long> {
    List<MaintenanceLog> findByAssetId(Long assetId);
}
