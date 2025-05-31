package com.example.EnterpriseAssetMonitoringSystem.assettest;

import com.example.EnterpriseAssetMonitoringSystem.dto.AssetDTO;
import com.example.EnterpriseAssetMonitoringSystem.entity.Asset;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.repository.AssetRepository;
import com.example.EnterpriseAssetMonitoringSystem.repository.UserRepository;
import com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest.AssetService;
import com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest.UptimeLogService;
import com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AssetServiceTest {

    @InjectMocks
    private AssetService assetService;

    @Mock
    private AssetRepository assetRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private UserService userService;

    @Mock
    private UptimeLogService uptimeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAssetByManager_Success() {
        AssetDTO dto = new AssetDTO();
        dto.setName("Motor A");
        dto.setType("Motor");
        dto.setLocation("Plant 1");
        dto.setCreatedByUserId(1L);

        User manager = new User();
        manager.setId(1L);
        manager.setRole(Role.MANAGER);

        when(userService.getUserById(1L)).thenReturn(manager);
        when(assetRepo.save(any(Asset.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Asset saved = assetService.addAsset(dto);

        assertEquals("Motor A", saved.getName());
        verify(uptimeService).createUptime(any(Asset.class));
    }

    @Test
    void testAddAssetByOperator_ThrowsException() {
        AssetDTO dto = new AssetDTO();
        dto.setName("Motor B");
        dto.setType("Motor");
        dto.setLocation("Plant 2");
        dto.setCreatedByUserId(2L);

        User operator = new User();
        operator.setId(2L);
        operator.setRole(Role.OPERATOR);

        when(userService.getUserById(2L)).thenReturn(operator);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> assetService.addAsset(dto));
        assertEquals("Only MANAGERs can add assets", ex.getMessage());
    }

    @Test
    void testGetAllAssets() {
        List<Asset> mockList = Arrays.asList(new Asset(), new Asset());
        when(assetRepo.findAll()).thenReturn(mockList);

        List<Asset> result = assetService.getAllAssets();
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteAssetByManager() {
        User manager = new User();
        manager.setId(1L);
        manager.setRole(Role.MANAGER);

        when(userService.getUserById(1L)).thenReturn(manager);

        assetService.deleteAsset(10L, 1L);

        verify(assetRepo).deleteById(10L);
    }

    @Test
    void testDeleteAssetByOperator_ThrowsException() {
        User operator = new User();
        operator.setId(2L);
        operator.setRole(Role.OPERATOR);

        when(userService.getUserById(2L)).thenReturn(operator);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> assetService.deleteAsset(10L, 2L));
        assertEquals("Only MANAGERs can delete assets", ex.getMessage());
    }
}
