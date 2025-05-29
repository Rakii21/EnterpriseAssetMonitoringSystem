package com.example.EnterpriseAssetMonitoringSystem.repository;

import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository to access uptime logs.
 */
public interface UptimeLogRepository extends JpaRepository<UptimeLog, Long> {

    List<UptimeLog> findByAssetId(Long assetId);

//    Optional<UptimeLog> findFirstByAssetIdAndStatusAndEndTimeIsNullOrderByStartTimeDesc(Long assetId, Status status);
//
//    UptimeLog findFirstByAssetIdAndStatusOrderByStartTimeDesc(Long id, Status status);

    Optional<UptimeLog> findByAssetIdAndStatus(Long assetId,Status status);
}