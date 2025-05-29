package com.example.EnterpriseAssetMonitoringSystem.maintenanceTest;

import com.example.EnterpriseAssetMonitoringSystem.controller.MaintenanceController;
import com.example.EnterpriseAssetMonitoringSystem.dto.CompleteMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.dto.ScheduleMaintenanceDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.MaintenanceLog;
import com.example.EnterpriseAssetMonitoringSystem.service.MaintenanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class MaintenanceControllerTest {

    @Mock private MaintenanceService maintenanceService;
    @InjectMocks private MaintenanceController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSchedule() {
        ScheduleMaintenanceDTO dto = new ScheduleMaintenanceDTO();
        dto.setAssetId(1L);
        dto.setScheduledDate(LocalDate.now());

        MaintenanceLog log = new MaintenanceLog();
        log.setScheduledDate(dto.getScheduledDate());

        when(maintenanceService.scheduleMaintenance(dto, 100L)).thenReturn(log);

        MaintenanceLog result = controller.schedule(dto, 100L);

        assertNotNull(result);
        assertEquals(dto.getScheduledDate(), result.getScheduledDate());
        verify(maintenanceService, times(1)).scheduleMaintenance(dto, 100L);
    }

    @Test
    void testComplete() {
        CompleteMaintenanceDTO dto = new CompleteMaintenanceDTO();
        dto.setLogId(1L);
        dto.setCompletedDate(LocalDate.now());
        dto.setRemarks("Fixed");

        MaintenanceLog log = new MaintenanceLog();
        log.setRemarks("Fixed");

        when(maintenanceService.completeMaintenance(dto, 200L)).thenReturn(log);

        MaintenanceLog result = controller.complete(dto, 200L);

        assertEquals("Fixed", result.getRemarks());
        verify(maintenanceService, times(1)).completeMaintenance(dto, 200L);
    }

    @Test
    void testGetLogsByAsset() {
        MaintenanceLog log1 = new MaintenanceLog();
        MaintenanceLog log2 = new MaintenanceLog();

        when(maintenanceService.getLogsByAsset(3L)).thenReturn(Arrays.asList(log1, log2));

        List<MaintenanceLog> result = controller.getLogsByAsset(3L);

        assertEquals(2, result.size());
        verify(maintenanceService, times(1)).getLogsByAsset(3L);
    }
}