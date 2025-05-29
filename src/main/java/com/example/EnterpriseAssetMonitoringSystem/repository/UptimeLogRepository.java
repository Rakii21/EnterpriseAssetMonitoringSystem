package com.example.EnterpriseAssetMonitoringSystem.repository;

import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import org.springframework.data.jpa.repository.JpaRepository;


//Repo to manage the uptime log entry for all the asset
import java.util.List;

public interface UptimeLogRepository extends JpaRepository<Uptimelog, Long> {
    List<Uptimelog> findByAssetid(Long assetid);
}
