package com.example.EnterpriseAssetMonitoringSystem.usertest;

import com.example.EnterpriseAssetMonitoringSystem.entity.*;
import com.example.EnterpriseAssetMonitoringSystem.repository.UserRepository;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock UserRepository userRepo;
    @Mock org.springframework.security.crypto.password.PasswordEncoder encoder;
    @InjectMocks UserService service;

    User manager, user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        manager = new User(1L, "Manager", "manager@gmail.com", "pass", Role.MANAGER);
        user = new User(2L, "Operator", "operator@gmail.com", "pass", Role.OPERATOR);
    }

    @Test void register_userSavedWithEncodedPassword() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode("pass")).thenReturn("encoded");
        when(userRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        User saved = service.register(user);
        assertEquals("encoded", saved.getPassword());
    }

    @Test void login_validCredentials_success() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(encoder.matches("pass", "pass")).thenReturn(true);

        assertEquals(user, service.login(user.getEmail(), "pass"));
    }

    @Test void getAllUsers_returnsList() {
        when(userRepo.findAll()).thenReturn(List.of(user, manager));
        assertEquals(2, service.getAllUsers().size());
    }

    @Test void getUserById_found() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(manager));
        assertEquals(manager, service.getUserById(1L));
    }

    @Test void updateRoleByManager_validRequest_roleUpdated() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(manager));
        when(userRepo.findById(2L)).thenReturn(Optional.of(user));
        when(userRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        User updated = service.updateRoleByManager(1L, 2L, "MANAGER");
        assertEquals(Role.MANAGER, updated.getRole());
    }
}
