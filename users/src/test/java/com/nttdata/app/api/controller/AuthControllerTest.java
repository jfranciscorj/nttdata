package com.nttdata.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.app.api.dto.request.auth.LoginRequest;
import com.nttdata.app.api.dto.response.auth.LoginResponse;
import com.nttdata.app.core.application.usecase.AuthUserCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUserCase authService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequest loginRequest;
    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest("user@test.com", "Password123*");
        UUID id = UUID.randomUUID();
        loginResponse = new LoginResponse(id, "fake-email@test.com", "fake-jwt-token");
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Mockear servicio
        Mockito.when(authService.authLogin(any(LoginRequest.class)))
                .thenReturn(loginResponse);

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.email").value("fake-eemail@test.com"));
    }

    @Test
    void testLoginFailure() throws Exception {
        // Simular error en el servicio
        Mockito.when(authService.authLogin(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is4xxClientError()); // aquí ajusta a 401 si tu GlobalExceptionHandler lo maneja
    }
}
