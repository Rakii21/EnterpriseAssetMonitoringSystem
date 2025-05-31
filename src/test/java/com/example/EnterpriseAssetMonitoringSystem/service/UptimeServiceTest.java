package com.example.EnterpriseAssetMonitoringSystem.service;

import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog.Status;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.UptimeLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UptimeServiceTest {

    @Mock private UptimeLogRepository uptimeRepo;
    @Mock private AssetRepository assetRepo;
    @Mock private UserService userService;

    @InjectMocks
    private UptimeLogService uptimeService;

    private Asset asset;
    private UptimeLog uptimeLog;

    @BeforeEach
    void setUp() {
        openMocks(this);

        asset = new Asset();
        asset.setId(1L);

        uptimeLog = new UptimeLog();
        uptimeLog.setId(1L);
        uptimeLog.setAsset(asset);
        uptimeLog.setStartTime(LocalDateTime.now().minusHours(1));
        uptimeLog.setStatus(Status.UP);
    }

    @Test
    void testCreateUptime_Success() {
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));

        uptimeService.createUptime(asset);

        verify(uptimeRepo, times(1)).save(any(UptimeLog.class));
    }

    @Test
    void testCreateDownTime_Success() {
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(uptimeRepo.findByAssetIdAndStatus(1L, Status.UP)).thenReturn(Optional.of(uptimeLog));

        uptimeService.createDownTime(asset);

        verify(uptimeRepo, times(2)).save(any(UptimeLog.class)); // 1 updated UP + 1 new DOWN
    }

    @Test
    void testUpdateDownTime_Success() {
        uptimeLog.setStatus(Status.DOWN);
        uptimeLog.setEndTime(null);

        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(uptimeRepo.findByAssetIdAndStatus(1L, Status.DOWN)).thenReturn(Optional.of(uptimeLog));

        uptimeService.updateDownTime(asset);

        verify(uptimeRepo, times(2)).save(any(UptimeLog.class)); // 1 updated DOWN + 1 new UP
    }

    @Test
    void testGetLogsByAsset() {
        when(uptimeRepo.findByAssetId(1L)).thenReturn(List.of(new UptimeLog(), new UptimeLog()));

        List<UptimeLog> logs = uptimeService.getLogsByAsset(1L);

        assertEquals(2, logs.size());
        verify(uptimeRepo, times(1)).findByAssetId(1L);
    }
}
