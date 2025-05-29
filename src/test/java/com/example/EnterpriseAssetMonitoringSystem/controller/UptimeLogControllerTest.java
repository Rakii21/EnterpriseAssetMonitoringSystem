package com.example.EnterpriseAssetMonitoringSystem.UptimeTest;

import com.example.EnterpriseAssetMonitoringSystem.controller.UptimeLogController;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import com.example.EnterpriseAssetMonitoringSystem.service.UptimeLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UptimeLogControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UptimeLogService uptimeLogService;

    @InjectMocks
    private UptimeLogController uptimeLogController;

    @BeforeEach
    void setUp() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(uptimeLogController).build();
    }

    @Test
    void testGetLogsByAsset_ReturnsList() throws Exception {
        // Sample asset and uptime log data
        Asset asset = new Asset();
        asset.setId(1L);

        UptimeLog log1 = new UptimeLog();
        log1.setId(100L);
        log1.setAsset(asset);
        log1.setStartTime(LocalDateTime.now().minusHours(2));
        log1.setEndTime(LocalDateTime.now());
        log1.setStatus(UptimeLog.Status.UP);

        UptimeLog log2 = new UptimeLog();
        log2.setId(101L);
        log2.setAsset(asset);
        log2.setStartTime(LocalDateTime.now().minusDays(1));
        log2.setEndTime(LocalDateTime.now().minusHours(20));
        log2.setStatus(UptimeLog.Status.DOWN);

        when(uptimeLogService.getLogsByAsset(1L)).thenReturn(List.of(log1, log2));

        mockMvc.perform(get("/api/uptime/asset/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[1].id").value(101L));

        verify(uptimeLogService, times(1)).getLogsByAsset(1L);
    }
}
