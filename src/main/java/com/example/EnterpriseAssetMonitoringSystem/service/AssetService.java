package com.example.EnterpriseAssetMonitoringSystem.service;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.UserRepository;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    // Repositories for asset operation and user operation
    private final AssetRepository assetRepo;
    private final UserRepository userRepo;
    // Method to add a new asset
    public Asset addAsset(Asset asset){
        return assetRepo.save(asset);
    }
    // Method to retrieve all assets
    public List<Asset> getAllAssets(){
        return assetRepo.findAll();
    }
    // Method to retrieve all asset by asset ID
    public Asset getAssetById(Long id){
        return assetRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
    }
    // Method to update an existing asset
    public Asset updateAsset(Long id, Asset updated){
        Asset asset = getAssetById(id);
        asset.setName(updated.getName());
        asset.setType(updated.getType());
        asset.setLocation(updated.getLocation());
        asset.setThresholdTemp(updated.getThresholdTemp());
        asset.setThresholdPressure(updated.getThresholdPressure());
        return assetRepo.save(asset);
    }
    // Method to delete an asset by its ID
    public void deleteAsset(Long id){
        assetRepo.deleteById(id);
    }
    // Method to assign an asset to user
    public Asset assignToUser(Long assetId, Long userId){
        Asset asset = getAssetById(assetId);
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        asset.setAssignedTo(user);
        return assetRepo.save(asset);
    }
    // Method to get assets assigned to a specific user
    public List<Asset> getAssetByUser(Long id){
        return assetRepo.findByAssignedToId(id);
    }
}
