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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import org.springframework.http.HttpStatus;

public class MaintenanceControllerTest {

    @Mock private MaintenanceService maintenanceService;
    @Mock private BindingResult bindingResult;
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

        when(bindingResult.hasErrors()).thenReturn(false);
        when(maintenanceService.scheduleMaintenance(dto, 100L)).thenReturn(log);

        ResponseEntity<?> response = controller.schedule(dto, bindingResult, 100L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof MaintenanceLog);
        assertEquals(dto.getScheduledDate(), ((MaintenanceLog) response.getBody()).getScheduledDate());

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

        when(bindingResult.hasErrors()).thenReturn(false);
        when(maintenanceService.completeMaintenance(dto, 200L)).thenReturn(log);

        ResponseEntity<?> response = controller.complete(dto, bindingResult, 200L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof MaintenanceLog);
        assertEquals("Fixed", ((MaintenanceLog) response.getBody()).getRemarks());

        verify(maintenanceService, times(1)).completeMaintenance(dto, 200L);
    }

    @Test
    void testGetLogsByAsset() {
        MaintenanceLog log1 = new MaintenanceLog();
        MaintenanceLog log2 = new MaintenanceLog();

        when(maintenanceService.getLogsByAsset(3L)).thenReturn(Arrays.asList(log1, log2));

        ResponseEntity<List<MaintenanceLog>> response = controller.getLogsByAsset(3L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        verify(maintenanceService, times(1)).getLogsByAsset(3L);
    }
}
