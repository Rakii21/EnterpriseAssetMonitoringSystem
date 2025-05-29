package com.example.EnterpriseAssetMonitoringSystem.maintenanceTest;

import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.MaintenanceLogRepository;
import com.example.EnterpriseAssetMonitoringSystem.service.MaintenanceService;
import com.example.EnterpriseAssetMonitoringSystem.service.UptimeService;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class MaintenanceServiceTest {

    @Mock private MaintenanceLogRepository maintenanceRepo;
    @Mock private AssetRepository assetRepo;
    @Mock private UserService userService;
    @Mock private UptimeService uptimeService;

    @InjectMocks
    private MaintenanceService maintenanceService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testScheduleMaintenance_Success() {
        ScheduleMaintenanceDTO dto = new ScheduleMaintenanceDTO();
        dto.setAssetId(1L);
        dto.setScheduledDate(LocalDate.now());

        User user = new User();
        user.setRole(Role.OPERATOR);

        Asset asset = new Asset();
        asset.setId(1L);

        MaintenanceLog log = new MaintenanceLog();
        log.setAsset(asset);
        log.setScheduledDate(dto.getScheduledDate());

        when(userService.getUserById(10L)).thenReturn(user);
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));
        when(maintenanceRepo.save(any(MaintenanceLog.class))).thenReturn(log);

        MaintenanceLog result = maintenanceService.scheduleMaintenance(dto, 10L);

        assertNotNull(result);
        assertEquals(asset, result.getAsset());
        verify(uptimeService, times(1)).createDownTime(asset);
    }

    @Test
    void testCompleteMaintenance_Success() {
        CompleteMaintenanceDTO dto = new CompleteMaintenanceDTO();
        dto.setLogId(1L);
        dto.setCompletedDate(LocalDate.now());
        dto.setRemarks("Done");

        User user = new User();
        user.setRole(Role.OPERATOR);

        Asset asset = new Asset();
        MaintenanceLog log = new MaintenanceLog();
        log.setAsset(asset);

        when(userService.getUserById(5L)).thenReturn(user);
        when(maintenanceRepo.findById(1L)).thenReturn(Optional.of(log));
        when(maintenanceRepo.save(log)).thenReturn(log);

        MaintenanceLog result = maintenanceService.completeMaintenance(dto, 5L);

        assertEquals("Done", result.getRemarks());
        verify(uptimeService, times(1)).updateDownTime(asset);
    }

    @Test
    void testGetLogsByAsset() {
        Asset asset = new Asset();
        asset.setId(2L);

        MaintenanceLog log1 = new MaintenanceLog();
        MaintenanceLog log2 = new MaintenanceLog();

        when(maintenanceRepo.findByAssetId(2L)).thenReturn(List.of(log1, log2));

        List<MaintenanceLog> result = maintenanceService.getLogsByAsset(2L);

        assertEquals(2, result.size());
        verify(maintenanceRepo, times(1)).findByAssetId(2L);
    }
}