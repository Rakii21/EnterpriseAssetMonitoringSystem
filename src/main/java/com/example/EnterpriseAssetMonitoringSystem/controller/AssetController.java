package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.AssetDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.service.AssetService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Tag(name="Asset")
@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public Asset addAsset(@RequestBody @Valid AssetDTO dto) {
        return assetService.addAsset(dto);
    }

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Long id) {
        return assetService.getAssetById(id);
    }

    @PutMapping("/{id}")
    public Asset updateAsset(
            @PathVariable Long id,
            @RequestBody Asset asset,
            @RequestParam Long userId) {
        return assetService.updateAsset(id, asset, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteAsset(@PathVariable Long id, @RequestParam Long userId) {
        assetService.deleteAsset(id, userId);
        return "Asset deleted successfully";
    }

    @PutMapping("/{assetId}/assign/{userId}")
    public Asset assignAssetToUser(
            @PathVariable Long assetId,
            @PathVariable Long userId,
            @RequestParam Long managerId) {
        return assetService.assignToUser(assetId, userId, managerId);
    }

    @GetMapping("/assigned/{userId}")
    public List<Asset> getAssetsByUser(@PathVariable Long userId) {
        return assetService.getAssetsByUser(userId);
    }
}