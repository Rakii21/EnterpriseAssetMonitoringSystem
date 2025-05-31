package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import com.example.EnterpriseAssetMonitoringSystem.exception.ObjectNotFoundException;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.UptimeLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UptimeLogService {

    private final UptimeLogRepository uptimeRepo;
    private final AssetRepository assetRepo;
    private final UserService userService;

    public void createUptime(Asset asset1) {
        Asset asset = assetRepo.findById(asset1.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Asset not found"));

        UptimeLog log = new UptimeLog();
        log.setAsset(asset);
        log.setStartTime(LocalDateTime.now());
        log.setStatus(UptimeLog.Status.UP);

        uptimeRepo.save(log);
    }
    public void createDownTime(Asset asset1){
        Asset asset = assetRepo.findById(asset1.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Asset not found"));
        Optional<UptimeLog> uptime = uptimeRepo.findByAssetIdAndStatus(asset.getId(), UptimeLog.Status.UP);
        UptimeLog up1 = uptime.get();
        if(up1.getStatus() == UptimeLog.Status.UP && up1.getEndTime() == null){
            up1.setEndTime(LocalDateTime.now());
            uptimeRepo.save(up1);
        }
        UptimeLog downtime=new UptimeLog();
        downtime.setAsset(asset);
        downtime.setStartTime(LocalDateTime.now());
        downtime.setStatus(UptimeLog.Status.DOWN);
        uptimeRepo.save(downtime);

    }

    public void updateDownTime(Asset asset1) {
        Asset asset = assetRepo.findById(asset1.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Asset not found"));
        Optional<UptimeLog> downtime = uptimeRepo.findByAssetIdAndStatus(asset.getId(), UptimeLog.Status.DOWN);

        if (downtime.isPresent()) {
            UptimeLog down1 = downtime.get();
            if (down1.getStatus() == UptimeLog.Status.DOWN && down1.getEndTime() == null) {
                down1.setEndTime(LocalDateTime.now()); // Set end time to the completed date
                uptimeRepo.save(down1);
            }
        }

        UptimeLog uptime = new UptimeLog();
        uptime.setAsset(asset);
        uptime.setStartTime(LocalDateTime.now());
        uptime.setStatus(UptimeLog.Status.UP);
        uptimeRepo.save(uptime);
    }

    public List<UptimeLog> getLogsByAsset(Long assetId) {

        return uptimeRepo.findByAssetId(assetId);
    }

}