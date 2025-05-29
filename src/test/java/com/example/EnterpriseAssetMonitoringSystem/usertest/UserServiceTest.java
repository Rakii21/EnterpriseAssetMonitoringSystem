package com.example.EnterpriseAssetMonitoringSystem.usertest;

import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.repository.UserRepository;
import com.example.EnterpriseAssetMonitoringSystem.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepo;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_ShouldSaveUser_WhenEmailNotTaken() {
        User user = new User(null, "abc", "abc@gamil.com", "123", Role.OPERATOR);
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User saved = userService.register(user);

        assertEquals("encoded123", saved.getPassword());
        verify(userRepo).save(saved);
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsValid() {
        User user = new User(1L, "abc", "abc@gmail.com", "encoded123", Role.OPERATOR);
        when(userRepo.findByEmail("abc@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123", "encoded123")).thenReturn(true);

        User result = userService.login("abc@gmail.com", "123");

        assertEquals("abc", result.getName());
    }

    @Test
    void getAllUsers_ShouldReturnUserList() {
        List<User> users = List.of(new User(1L, "abc", "abc@gmail.com", "pass", Role.OPERATOR));
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }

    @Test
    void updateRoleByManager_ShouldUpdateRole_WhenRequesterIsManager() {
        User manager = new User(1L, "Manager", "m@gmail.com", "pass", Role.MANAGER);
        User operator = new User(2L, "User", "u@egmail.com", "pass", Role.OPERATOR);

        when(userRepo.findById(1L)).thenReturn(Optional.of(manager));
        when(userRepo.findById(2L)).thenReturn(Optional.of(operator));
        when(userRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        User updated = userService.updateRoleByManager(1L, 2L, "MANAGER");

        assertEquals(Role.MANAGER, updated.getRole());
    }

}
