package com.example.EnterpriseAssetMonitoringSystem;

import com.example.EnterpriseAssetMonitoringSystem.dto.SensorDataDTO;
import com.example.EnterpriseAssetMonitoringSystem.repository.SensorDataRepository;
import com.example.EnterpriseAssetMonitoringSystem.service.SensorDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class SensorDataServiceTest {

    @Mock
    private SensorDataRepository sensorRepo;
    @Mock
    private AssetRepository assetRepo;
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private SensorDataService sensorDataService;

    @Test
    public void testSaveSensorData(){

        SensorDataDTO dto =new SensorDataDTO();
        dto.setAssetId(1L);

        Asset asset =new Asset();

    }



}
