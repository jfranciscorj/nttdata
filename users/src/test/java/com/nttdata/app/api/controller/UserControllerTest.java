package com.nttdata.app.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.app.api.dto.request.user.CreateUserRequest;
import com.nttdata.app.api.dto.request.user.UpdateUserRequest;
import com.nttdata.app.config.TestSecurityConfig;
import com.nttdata.app.core.application.usecase.CreateUserUseCase;
import com.nttdata.app.core.application.usecase.DeleteUserUseCase;
import com.nttdata.app.core.application.usecase.GetUserUseCase;
import com.nttdata.app.core.application.usecase.UpdateUserUseCase;
import com.nttdata.app.core.domain.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserUseCase createUserUseCase;

    @MockBean
    private DeleteUserUseCase deleteUserUseCase;

    @MockBean
    private GetUserUseCase getUserUseCase;

    @MockBean
    private UpdateUserUseCase updateUserUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private UserModel sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new UserModel();
        sampleUser.setId(UUID.randomUUID());
        sampleUser.setName("Juan");
        sampleUser.setEmail("juan@test.com");
        sampleUser.setPassword("secret");
        sampleUser.setActive(true);
        sampleUser.setCreateAt(LocalDateTime.now());
        sampleUser.setModifiedAt(LocalDateTime.now());
        sampleUser.setLastLogin(LocalDateTime.now());
        sampleUser.setToken("fake-token");
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("Juan");
        request.setEmail("juan@test.com");
        request.setPassword("Secret123+");

        Mockito.when(createUserUseCase.createUserUseCase(any())).thenReturn(sampleUser);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleUser.getId().toString()))
                .andExpect(jsonPath("$.token").value("fake-token"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        Mockito.when(getUserUseCase.getAllUserUserCase()).thenReturn(List.of(sampleUser));

        mockMvc.perform(get("/v1/users")
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].correo").value("juan@test.com"));
    }

    @Test
    void testGetByIdFound() throws Exception {
        Mockito.when(getUserUseCase.getUserByIdUserCase(any()))
                .thenReturn(Optional.of(sampleUser));

        mockMvc.perform(get("/v1/users/{id}", sampleUser.getId())
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleUser.getId().toString()));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(getUserUseCase.getUserByIdUserCase(any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/users/{id}", UUID.randomUUID())
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser() throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("Pedro");
        request.setPassword("newpass");

        sampleUser.setName("Pedro");

        Mockito.when(updateUserUseCase.updateUserUseCase(any(), any()))
                .thenReturn(sampleUser);

        mockMvc.perform(put("/v1/users/{id}", sampleUser.getId())
                        .header("Authorization", "Bearer fake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void testPartialUpdateUser() throws Exception {
        Map<String, Object> fields = Map.of("nombre", "Carlos");
        sampleUser.setName("Carlos");

        Mockito.when(updateUserUseCase.updatePartialUserCase(any(), any()))
                .thenReturn(sampleUser);

        mockMvc.perform(patch("/v1/users/{id}", sampleUser.getId())
                        .header("Authorization", "Bearer fake")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fields)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/v1/users/{id}", sampleUser.getId())
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isOk());

        Mockito.verify(deleteUserUseCase).deleteUserUseCase(eq(sampleUser.getId()));
    }

    @Test
    void testEnableUserStatus() throws Exception {
        sampleUser.setActive(true);
        Mockito.when(updateUserUseCase.enableStatusUserCase(any())).thenReturn(sampleUser);

        mockMvc.perform(patch("/v1/users/{id}/enable", sampleUser.getId())
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void testDisableUserStatus() throws Exception {
        sampleUser.setActive(false);
        Mockito.when(updateUserUseCase.disableStatusUserCase(any())).thenReturn(sampleUser);

        mockMvc.perform(patch("/v1/users/{id}/disable", sampleUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void testGetStatusFound() throws Exception {
        Mockito.when(getUserUseCase.getStatus(any())).thenReturn(Optional.of(sampleUser));

        mockMvc.perform(get("/v1/users/{id}/status", sampleUser.getId())
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void testGetStatusNotFound() throws Exception {
        Mockito.when(getUserUseCase.getStatus(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/users/{id}/status", sampleUser.getId())
                        .header("Authorization", "Bearer fake"))
                .andExpect(status().isNoContent());
    }
}
