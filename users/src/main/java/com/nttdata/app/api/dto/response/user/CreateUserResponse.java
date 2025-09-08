package com.nttdata.app.api.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class CreateUserResponse {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("creado")
    private LocalDateTime createAt;
    @JsonProperty("modificado")
    private LocalDateTime modifiedAt;
    @JsonProperty("ultimoLogin")
    private LocalDateTime lastLogin;
    @JsonProperty("token")
    private String token;
    @JsonProperty("activo")
    private boolean isActive;

    public static CreateUserResponse fromDomain(UserModel user) {
        CreateUserResponse response = new CreateUserResponse();
        response.id = user.getId();
        response.createAt = user.getCreateAt();
        response.modifiedAt = user.getModifiedAt();
        response.lastLogin = user.getLastLogin();
        response.token = user.getToken();
        response.isActive = user.isActive();
        return response;
    }

}
