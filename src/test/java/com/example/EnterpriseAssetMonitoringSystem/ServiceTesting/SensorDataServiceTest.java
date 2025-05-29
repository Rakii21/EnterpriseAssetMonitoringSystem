package com.example.EnterpriseAssetMonitoringSystem.ServiceTesting;

import com.example.EnterpriseAssetMonitoringSystem.dto.SensorDataDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.SensorData;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.SensorDataRepository;
import com.example.EnterpriseAssetMonitoringSystem.service.SensorDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;
import org.springframework.jdbc.core.RowMapper;

public class SensorDataServiceTest {

    @Mock
    private SensorDataRepository sensorRepo;

    @Mock
    private AssetRepository assetRepo;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SensorDataService service;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testSaveSensorData() throws SQLException {
        // Create test input DTO
        SensorDataDTO dto = new SensorDataDTO();
        dto.setAssetId(1L);

        // Create mock Asset
        Asset asset = new Asset();
        asset.setId(1L);

        // Set mock behavior for assetRepo
        when(assetRepo.findById(1L)).thenReturn(Optional.of(asset));

        // Create expected SensorData object
        SensorData expectedData = new SensorData();
        expectedData.setAsset(asset);
        expectedData.setTemperature(25.0);
        expectedData.setPressure(1.02);
        expectedData.setTimestamp(LocalDateTime.now());

        // Simulate jdbcTemplate.queryForObject with basic mocking
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class))).thenReturn(expectedData);

        // Simulate sensorRepo.save() returning the saved object
        when(sensorRepo.save(expectedData)).thenReturn(expectedData);

        // Call the method under test
        SensorData actualData = service.saveSensorData(dto);

        // Verify and assert
        assertNotNull(actualData);
        assertEquals(25.0, actualData.getTemperature());
        assertEquals(1.02, actualData.getPressure());
        assertEquals(asset, actualData.getAsset());

        verify(assetRepo, times(1)).findById(1L);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class));
        verify(sensorRepo, times(1)).save(expectedData);
    }
}
