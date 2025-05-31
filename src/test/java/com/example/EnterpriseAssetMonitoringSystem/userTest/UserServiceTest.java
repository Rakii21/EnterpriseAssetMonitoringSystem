package com.example.EnterpriseAssetMonitoringSystem.userTest;

import com.example.EnterpriseAssetMonitoringSystem.entity.Role;
import com.example.EnterpriseAssetMonitoringSystem.entity.User;
import com.example.EnterpriseAssetMonitoringSystem.exception.UnauthorizedException;
import com.example.EnterpriseAssetMonitoringSystem.exception.UserInvalidException;
import com.example.EnterpriseAssetMonitoringSystem.exception.UserNotFoundException;
import com.example.EnterpriseAssetMonitoringSystem.repository.UserRepository;
import com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "John Doe", "john@example.com", "encodedPass", Role.OPERATOR);
    }

    @Test
    void register_whenEmailNotUsed_shouldSaveUser() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(userRepo.save(any(User.class))).thenReturn(user);

        User toRegister = new User(null, user.getName(), user.getEmail(), "rawPass", user.getRole());
        User registered = userService.register(toRegister);

        assertNotNull(registered);
        assertEquals(user.getEmail(), registered.getEmail());
        verify(passwordEncoder).encode("rawPass");
        verify(userRepo).save(any(User.class));
    }

    @Test
    void register_whenEmailAlreadyUsed_shouldThrowException() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User toRegister = new User(null, user.getName(), user.getEmail(), "rawPass", user.getRole());

        assertThrows(UserInvalidException.class, () -> userService.register(toRegister));
        verify(userRepo, never()).save(any());
    }

    @Test
    void login_whenCredentialsValid_shouldReturnUser() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawPass", user.getPassword())).thenReturn(true);

        User loggedIn = userService.login(user.getEmail(), "rawPass");

        assertNotNull(loggedIn);
        assertEquals(user.getEmail(), loggedIn.getEmail());
    }

    @Test
    void login_whenInvalidPassword_shouldThrowException() {
        when(userRepo.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", user.getPassword())).thenReturn(false);

        assertThrows(UserInvalidException.class, () -> userService.login(user.getEmail(), "wrongPass"));
    }

    @Test
    void login_whenEmailNotFound_shouldThrowException() {
        when(userRepo.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(UserInvalidException.class, () -> userService.login("unknown@example.com", "anyPass"));
    }

    @Test
    void getAllUsers_shouldReturnList() {
        List<User> users = List.of(user);
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(user.getEmail(), result.get(0).getEmail());
    }

    @Test
    void getUserById_whenUserExists_shouldReturnUser() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        User result = userService.getUserById(user.getId());

        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void getUserById_whenUserNotFound_shouldThrowException() {
        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(999L));
    }

    @Test
    void updateRole_whenUserExists_shouldUpdateRole() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateRole(user.getId(), "MANAGER");

        assertEquals(Role.MANAGER, updated.getRole());
        verify(userRepo).save(user);
    }

    @Test
    void updateRole_whenUserNotFound_shouldThrowException() {
        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.updateRole(999L, "MANAGER"));
    }

    @Test
    void updateRoleByManager_whenRequesterIsManager_shouldUpdateRole() {
        User manager = new User(2L, "Manager", "manager@example.com", "pass", Role.MANAGER);
        when(userRepo.findById(manager.getId())).thenReturn(Optional.of(manager));
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User updated = userService.updateRoleByManager(manager.getId(), user.getId(), "MANAGER");

        assertEquals(Role.MANAGER, updated.getRole());
    }

    @Test
    void updateRoleByManager_whenRequesterIsNotManager_shouldThrowException() {
        User operator = new User(3L, "Operator", "op@example.com", "pass", Role.OPERATOR);
        when(userRepo.findById(operator.getId())).thenReturn(Optional.of(operator));

        assertThrows(UnauthorizedException.class, () -> userService.updateRoleByManager(operator.getId(), user.getId(), "MANAGER"));
    }

    @Test
    void validateUserRole_whenRoleMatches_shouldNotThrow() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.validateUserRole(user.getId(), user.getRole()));
    }

    @Test
    void validateUserRole_whenRoleDoesNotMatch_shouldThrow() {
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedException.class, () -> userService.validateUserRole(user.getId(), Role.MANAGER));
    }
}
