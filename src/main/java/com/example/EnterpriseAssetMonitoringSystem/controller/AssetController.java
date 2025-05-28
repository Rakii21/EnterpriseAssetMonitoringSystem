package com.example.EnterpriseAssetMonitoringSystem.controller;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets") // Base URL for asset-related endpoints
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService; // Service for asset operations
    // Endpoint to add a new asset
    @PostMapping
    public Asset addAsset(@RequestBody Asset asset){
        return assetService.addAsset(asset);
    }
    // Endpoint to retrieve all assets
    @GetMapping
    public List<Asset> getAllAssets(){
        return assetService.getAllAssets();
    }
    // Endpoint to retrieve an asset by its ID
    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable long id){
        return assetService.getAssetById(id);
    }
    // Endpoint to update an existing asset
    @PutMapping("/{id}")
    public Asset updateAsset(@PathVariable long id, @RequestBody Asset asset){
        return assetService.updateAsset(id, asset);
    }
    // Endpoint to delete an asset by its ID
    @DeleteMapping("/{id}")
    public String deleteAsset(@PathVariable long id){
        assetService.deleteAsset(id);
        return "Asset deleted successfully.";
    }
    // Endpoint to assign an asset to a user
    @PutMapping("/{assetId}/assign/{userId}")
    public Asset assignAssetToUser(@PathVariable Long assetId, @PathVariable Long userId){
        return assetService.assignToUser(assetId, userId);
    }
    // Endpoint to get assets assigned to a specific user
    @GetMapping("/assigned/{userId}")
    public List<Asset> getAssetByUser(@PathVariable Long userId){
        return assetService.getAssetByUser(userId);
    }
}
