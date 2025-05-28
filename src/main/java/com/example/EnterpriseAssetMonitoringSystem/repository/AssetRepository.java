package com.example.EnterpriseAssetMonitoringSystem.repository;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByAssignedToId(Long userId); //Query method to find asset assigned to a specific user by their id.
}
