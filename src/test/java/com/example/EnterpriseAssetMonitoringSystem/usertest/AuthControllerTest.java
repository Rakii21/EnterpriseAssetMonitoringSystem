package com.example.EnterpriseAssetMonitoringSystem.usertest;

import com.example.EnterpriseAssetMonitoringSystem.controller.AuthController;
import com.example.EnterpriseAssetMonitoringSystem.dto.LoginRequest;
import com.example.EnterpriseAssetMonitoringSystem.dto.RegisterRequest;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private AuthController authController;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldCallServiceAndReturnMessage() {
        RegisterRequest req = new RegisterRequest();
        req.setName("abc");
        req.setEmail("abc@egmail.com");
        req.setPassword("123");
        req.setRole(Role.OPERATOR);

        when(userService.register(any(User.class))).thenReturn(new User());

        String response = authController.register(req);

        assertEquals("User registered successfully", response);
        verify(userService).register(any(User.class));
    }

    @Test
    void login_ShouldCallServiceAndReturnSuccess() {
        LoginRequest req = new LoginRequest();
        req.setEmail("abc@gmail.com");
        req.setPassword("123");

        when(userService.login("abc@gmail.com", "123")).thenReturn(new User());

        String result = authController.login(req);

        assertEquals("Login successful", result);
    }
}
