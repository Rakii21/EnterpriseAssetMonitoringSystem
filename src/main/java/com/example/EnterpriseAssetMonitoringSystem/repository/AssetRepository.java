package com.example.EnterpriseAssetMonitoringSystem.repository;

import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface to manage Asset data access.
 */
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByAssignedToId(Long userId);
}
