package com.example.EnterpriseAssetMonitoringSystem.usertest;

import com.example.EnterpriseAssetMonitoringSystem.controller.UserController;
import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnList() {
        List<User> mockList = List.of(new User(1L, "abc", "abc@example.com", "pass", Role.OPERATOR));
        when(userService.getAllUsers()).thenReturn(mockList);

        List<User> result = userController.getAllUsers();

        assertEquals(1, result.size());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        User user = new User(1L, "abc", "abc@example.com", "pass", Role.OPERATOR);
        when(userService.getUserById(1L)).thenReturn(user);

        User result = userController.getUserById(1L);

        assertEquals("abc", result.getName());
    }

    @Test
    void updateUserRole_ShouldReturnUpdatedUser() {
        User updated = new User(2L, "User", "u@gmail.com", "pass", Role.MANAGER);
        when(userService.updateRoleByManager(1L, 2L, "MANAGER")).thenReturn(updated);

        User result = userController.updateUserRole(2L, "MANAGER", 1L);

        assertEquals(Role.MANAGER, result.getRole());
    }
}
