package com.nttdata.app.core.application.usecase.impl;

import com.nttdata.app.core.application.validator.RequestInputValidator;
import com.nttdata.app.core.domain.user.UserModel;
import com.nttdata.app.core.port.UserRepositoryPort;
import com.nttdata.app.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepositoryPort userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private RequestInputValidator validator;

    private UserServiceImpl service;

    private UserModel sampleUser;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepositoryPort.class);
        jwtUtil = mock(JwtUtil.class);
        passwordEncoder = mock(PasswordEncoder.class);
        validator = mock(RequestInputValidator.class);

        service = new UserServiceImpl(userRepository, jwtUtil, passwordEncoder, validator);

        sampleUser = new UserModel();
        sampleUser.setId(UUID.randomUUID());
        sampleUser.setName("Juan");
        sampleUser.setEmail("juan@test.com");
        sampleUser.setPassword("Password123*");
        sampleUser.setActive(true);
        sampleUser.setCreateAt(LocalDateTime.now());
        sampleUser.setModifiedAt(LocalDateTime.now());
        sampleUser.setLastLogin(LocalDateTime.now());
        sampleUser.setToken("token-xyz");
    }

    @Test
    void testCreateUserSuccess() {
        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.empty());
        when(jwtUtil.generateToken("juan@test.com")).thenReturn("fake-token");
        when(passwordEncoder.encode("Password123*")).thenReturn("encoded-pass");
        when(userRepository.save(any(UserModel.class))).thenReturn(sampleUser);

        UserModel result = service.createUserUseCase(sampleUser);

        assertNotNull(result);
        assertEquals("Juan", result.getName());
        verify(validator).validateNombreFormat("Juan");
        verify(userRepository).save(any(UserModel.class));
    }

    @Test
    void testCreateUser_EmailAlreadyExists() {
        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(sampleUser));

        assertThrows(IllegalArgumentException.class,
                () -> service.createUserUseCase(sampleUser));
    }

    @Test
    void testDeleteUser() {
        UUID id = UUID.randomUUID();
        service.deleteUserUseCase(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        List<UserModel> users = service.getAllUserUserCase();

        assertEquals(1, users.size());
        assertEquals("Juan", users.get(0).getName());
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(sampleUser.getId())).thenReturn(Optional.of(sampleUser));

        Optional<UserModel> user = service.getUserByIdUserCase(sampleUser.getId());

        assertTrue(user.isPresent());
        assertEquals("Juan", user.get().getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(sampleUser.getId())).thenReturn(Optional.empty());

        Optional<UserModel> user = service.getUserByIdUserCase(sampleUser.getId());

        assertTrue(user.isEmpty());
    }

    @Test
    void testUpdatePartialUserCase_UpdateName() {
        UUID id = sampleUser.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(UserModel.class))).thenReturn(sampleUser);

        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", "Pedro");

        UserModel updated = service.updatePartialUserCase(id, updates);

        assertEquals("Pedro", updated.getName());
        verify(userRepository).save(sampleUser);
    }

    @Test
    void testUpdatePartialUserCase_UpdatePassword() {
        UUID id = sampleUser.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-pass");
        when(userRepository.save(any(UserModel.class))).thenReturn(sampleUser);

        Map<String, Object> updates = new HashMap<>();
        updates.put("clave", "NewPassword123*");

        UserModel updated = service.updatePartialUserCase(id, updates);

        assertEquals("encoded-pass", updated.getPassword());
    }

    @Test
    void testUpdatePartialUserCase_NotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updatePartialUserCase(id, Map.of("nombre", "Pedro")));
    }

    @Test
    void testUpdateUserCase_Success() {
        UUID id = sampleUser.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-pass");
        when(userRepository.save(any(UserModel.class))).thenReturn(sampleUser);

        UserModel request = new UserModel();
        request.setName("Nuevo Nombre");
        request.setPassword("NewPassword123*");

        UserModel updated = service.updateUserUseCase(id, request);

        assertEquals("Nuevo Nombre", updated.getName());
        assertEquals("encoded-pass", updated.getPassword());
    }

    @Test
    void testUpdateUserCase_NotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserModel request = new UserModel();
        request.setName("Nuevo");
        request.setPassword("pass");

        assertThrows(RuntimeException.class,
                () -> service.updateUserUseCase(id, request));
    }

    @Test
    void testDisableStatusUserCase() {
        UUID id = sampleUser.getId();
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(UserModel.class))).thenReturn(sampleUser);

        UserModel result = service.disableStatusUserCase(id);

        assertFalse(result.isActive());
        verify(userRepository).save(sampleUser);
    }

    @Test
    void testEnableStatusUserCase() {
        UUID id = sampleUser.getId();
        sampleUser.setActive(false);
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(UserModel.class))).thenReturn(sampleUser);

        UserModel result = service.enableStatusUserCase(id);

        assertTrue(result.isActive());
        verify(userRepository).save(sampleUser);
    }
}