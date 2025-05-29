package com.example.EnterpriseAssetMonitoringSystem.SensorDataTests;

import com.example.EnterpriseAssetMonitoringSystem.controller.SensorDataController;
import com.example.EnterpriseAssetMonitoringSystem.dto.SensorDataDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.SensorData;
import com.example.EnterpriseAssetMonitoringSystem.service.SensorDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class SensorDataControllerTest {

    @Mock
    private SensorDataService sensorService;

    @InjectMocks
    private SensorDataController controller;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveSensorData() {

        SensorDataDTO dto = new SensorDataDTO();
        dto.setAssetId(1L);

        Asset asset = new Asset();
        asset.setId(1L);

        SensorData sensorData = new SensorData();
        sensorData.setAsset(asset);
        sensorData.setTemperature(25.0);
        sensorData.setPressure(1.01);
        sensorData.setTimestamp(LocalDateTime.now());

        when(sensorService.saveSensorData(dto)).thenReturn(sensorData);

        // Actual
        SensorData result = controller.saveSensorData(dto);

        // Assert
        assertNotNull(result);
        assertEquals(25.0, result.getTemperature());
        assertEquals(1.01, result.getPressure());
        assertEquals(1L, result.getAsset().getId());
        verify(sensorService, times(1)).saveSensorData(dto);
    }

    @Test
    void testGetSensorDataByAssetId() {

        Long assetId = 2L;
        SensorData data1 = new SensorData(1L, new Asset(), 22.5, 1.00, LocalDateTime.now());
        SensorData data2 = new SensorData(2L, new Asset(), 24.0, 1.02, LocalDateTime.now());

        //To test
        List<SensorData> mockList = Arrays.asList(data1, data2);

        when(sensorService.getDataByAssetId(assetId)).thenReturn(mockList);

        // Actual
        List<SensorData> result = controller.getSensorData(assetId);

        // Assert
        assertEquals(2, result.size());
        verify(sensorService, times(1)).getDataByAssetId(assetId);
    }
}
