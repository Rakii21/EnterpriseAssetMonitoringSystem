package com.example.EnterpriseAssetMonitoringSystem.assettest;

import com.example.EnterpriseAssetMonitoringSystem.controller.AssetController;
import com.example.EnterpriseAssetMonitoringSystem.dto.AssetDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.service.AssetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssetController.class)
public class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssetService assetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddAsset() throws Exception {
        AssetDTO dto = new AssetDTO();
        dto.setName("Pump A");
        dto.setType("Pump");
        dto.setLocation("Plant X");
        dto.setCreatedByUserId(1L);

        Asset asset = new Asset();
        asset.setId(1L);
        asset.setName("Pump A");

        Mockito.when(assetService.addAsset(any(AssetDTO.class))).thenReturn(asset);

        mockMvc.perform(post("/api/assets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pump A"));
    }

    @Test
    void testGetAllAssets() throws Exception {
        Asset asset1 = new Asset();
        asset1.setId(1L);
        asset1.setName("Motor A");

        Asset asset2 = new Asset();
        asset2.setId(2L);
        asset2.setName("Pump B");

        Mockito.when(assetService.getAllAssets()).thenReturn(Arrays.asList(asset1, asset2));

        mockMvc.perform(get("/api/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetAssetById() throws Exception {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setName("Machine 1");

        Mockito.when(assetService.getAssetById(1L)).thenReturn(asset);

        mockMvc.perform(get("/api/assets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Machine 1"));
    }

    @Test
    void testDeleteAsset() throws Exception {
        mockMvc.perform(delete("/api/assets/1?userId=10"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset deleted successfully"));
    }

    @Test
    void testAssignAssetToUser() throws Exception {
        Asset asset = new Asset();
        asset.setId(1L);
        asset.setName("Assigned Asset");

        Mockito.when(assetService.assignToUser(1L, 2L, 3L)).thenReturn(asset);

        mockMvc.perform(put("/api/assets/1/assign/2?managerId=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Assigned Asset"));
    }
}
