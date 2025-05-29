package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.dto.AssetDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepo;
    private final UserService userService;
    private final UptimeLogService uptimeService;

    public Asset addAsset(AssetDTO dto) {
        User user = userService.getUserById(dto.getCreatedByUserId());
        if (user.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only MANAGERs can add assets");
        }
        Asset asset = new Asset();
        asset.setName(dto.getName());
        asset.setType(dto.getType());
        asset.setLocation(dto.getLocation());
        asset.setThresholdTemp(dto.getThresholdTemp());
        asset.setThresholdPressure(dto.getThresholdPressure());
        Asset savedAsset = assetRepo.save(asset);
        uptimeService.createUptime(savedAsset);
        return savedAsset;
    }

    public List<Asset> getAllAssets() {
        return assetRepo.findAll();
    }

    public Asset getAssetById(Long id) {
        return assetRepo.findById(id).orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    public Asset updateAsset(Long id, Asset updated, Long userId) {
        User user = userService.getUserById(userId);
        if (user.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only MANAGERs can update assets");
        }
        Asset asset = getAssetById(id);
        asset.setName(updated.getName());
        asset.setType(updated.getType());
        asset.setLocation(updated.getLocation());
        asset.setThresholdTemp(updated.getThresholdTemp());
        asset.setThresholdPressure(updated.getThresholdPressure());
        return assetRepo.save(asset);
    }

    public void deleteAsset(Long id, Long userId) {
        User user = userService.getUserById(userId);
        if (user.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only MANAGERs can delete assets");
        }
        assetRepo.deleteById(id);
    }

    public Asset assignToUser(Long assetId, Long userId, Long assignedById) {
        User manager = userService.getUserById(assignedById);
        if (manager.getRole() != Role.MANAGER) {
            throw new RuntimeException("Only MANAGERs can assign assets");
        }
        Asset asset = getAssetById(assetId);
        User user = userService.getUserById(userId);
        asset.setAssignedTo(user);
        return assetRepo.save(asset);
    }

    public List<Asset> getAssetsByUser(Long userId) {
        return assetRepo.findByAssignedToId(userId);
    }
}
