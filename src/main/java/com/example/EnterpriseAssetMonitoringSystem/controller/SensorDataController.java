package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.SensorDataDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.SensorData;
import com.example.EnterpriseAssetMonitoringSystem.service.SensorDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Sensor Data")
@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorDataController {

    private final SensorDataService sensorService;

    @PostMapping("/send-data")
    public SensorData saveSensorData(@RequestBody @Valid SensorDataDTO dto){

        return sensorService.saveSensorData(dto);
    }

    @GetMapping("/asset/{assetId}")
    public List<SensorData> getSensorData(@PathVariable Long assetId){
        return sensorService.getDataByAssetId(assetId);
    }
}
