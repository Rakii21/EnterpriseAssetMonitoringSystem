package com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest;
import com.example.EnterpriseAssetMonitoringSystem.dto.SensorDataDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.SensorData;
import com.example.EnterpriseAssetMonitoringSystem.exception.ObjectNotFoundException;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;



@Service
public class SensorDataService {
    @Autowired
    private  SensorDataRepository sensorRepo;

    @Autowired
    private  AssetRepository assetRepo;

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    public  SensorData saveSensorData(SensorDataDTO dto){

        // Fetch Asset from DB....
        // dto.getAssetId() extracts the assetId from incoming request
        // tries to fetch corresponding Asset entity from the DB using assetRepo
        // if asset doesn't exist then it throws exception
        Asset asset = assetRepo.findById(dto.getAssetId()).orElseThrow(()->new ObjectNotFoundException("Asset with ID" + dto.getAssetId() + "not found"));

        //Fetch random row from dummy_sensor_data
        String sql="SELECT temperature, pressure FROM dummy_sensor_data ORDER BY RAND() LIMIT 1";

        //jdbcTemplate.queryForObject runs sql and retrieves one row
        //ResultSet contains random temperature and pressure value
        //it maps those values into a new SensorData object.

        SensorData data= jdbcTemplate.queryForObject(sql,(ResultSet rs,int rowNum)->{
            SensorData sensorData =new SensorData();
            sensorData.setAsset(asset);
            sensorData.setTemperature(rs.getDouble("temperature"));
            sensorData.setPressure(rs.getDouble("pressure"));
            sensorData.setTimestamp(LocalDateTime.now());
            return sensorData;

        });
        return sensorRepo.save(data);
    }
    // get all readings for a given asset
    public List<SensorData> getDataByAssetId(Long assetId){
        return sensorRepo.findByAssetId(assetId);
    }

}

