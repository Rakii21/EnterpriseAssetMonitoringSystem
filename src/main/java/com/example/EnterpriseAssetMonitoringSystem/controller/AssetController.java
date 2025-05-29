package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.AssetDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.service.AssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @PostMapping
    public ResponseEntity<?> addAsset(@RequestBody @Valid AssetDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldErrors()
                    , HttpStatus.BAD_REQUEST);}

        return ResponseEntity.ok(assetService.addAsset(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssetById(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(
            @PathVariable Long id,
            @RequestBody Asset asset,
            @RequestParam Long userId) {
        return ResponseEntity.ok(assetService.updateAsset(id, asset, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id, @RequestParam Long userId) {
        assetService.deleteAsset(id, userId);
        return ResponseEntity.ok("Asset deleted successfully");
    }

    @PutMapping("/{assetId}/assign/{userId}")
    public ResponseEntity<?> assignAssetToUser(
            @PathVariable Long assetId,
            @PathVariable Long userId,
            @RequestParam Long managerId) {
        return ResponseEntity.ok(assetService.assignToUser(assetId, userId, managerId));
    }

    @GetMapping("/assigned/{userId}")
    public ResponseEntity<?> getAssetsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(assetService.getAssetsByUser(userId));
    }
}
