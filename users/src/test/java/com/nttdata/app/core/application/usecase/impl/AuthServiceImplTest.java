package com.nttdata.app.core.application.usecase.impl;

import com.nttdata.app.api.dto.request.auth.LoginRequest;
import com.nttdata.app.api.dto.response.auth.LoginResponse;
import com.nttdata.app.api.exception.ServiceException;
import com.nttdata.app.core.domain.user.UserModel;
import com.nttdata.app.core.port.UserRepositoryPort;
import com.nttdata.app.core.util.ApiConstans;
import com.nttdata.app.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private UserRepositoryPort userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private AuthServiceImpl authService;

    private UserModel user;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepositoryPort.class);
        jwtUtil = mock(JwtUtil.class);
        passwordEncoder = mock(PasswordEncoder.class);

        authService = new AuthServiceImpl(userRepository, jwtUtil, passwordEncoder);

        user = new UserModel();
        user.setId(UUID.randomUUID());
        user.setEmail("juan@test.com");
        user.setPassword("encodedPass");
        user.setActive(true);
        user.setCreateAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
    }

    @Test
    void testAuthLogin_Success() {
        LoginRequest request = new LoginRequest("juan@test.com", "Password123*");

        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Password123*", "encodedPass")).thenReturn(true);
        when(jwtUtil.generateToken("juan@test.com")).thenReturn("fake-token");
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        LoginResponse response = authService.authLogin(request);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals("juan@test.com", response.getEmail());
        assertEquals("fake-token", response.getAuthToken());
        verify(userRepository).save(user);
    }

    @Test
    void testAuthLogin_UserNotFound() {
        LoginRequest request = new LoginRequest("notfound@test.com", "Password123*");

        when(userRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> authService.authLogin(request));
    }

    @Test
    void testAuthLogin_InvalidPassword() {
        LoginRequest request = new LoginRequest("juan@test.com", "wrongPass");

        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);

        ServiceException ex = assertThrows(ServiceException.class,
                () -> authService.authLogin(request));

        assertEquals("Incorrect username or password.", ex.getMessage());
        assertEquals(ApiConstans.INVALID_LOGIN, ex.getMessage());
    }

    @Test
    void testAuthLogin_UserInactive() {
        user.setActive(false);
        LoginRequest request = new LoginRequest("juan@test.com", "Password123*");

        when(userRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Password123*", "encodedPass")).thenReturn(true);

        ServiceException ex = assertThrows(ServiceException.class,
                () -> authService.authLogin(request));

        assertEquals("User is inactive.", ex.getMessage());
        assertEquals(ApiConstans.USER_INACTIVE, ex.getMessage());
    }
}
