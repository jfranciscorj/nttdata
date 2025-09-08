package com.nttdata.app.api.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class LoginResponse {

    private UUID id;
    @JsonProperty("correo")
    private String email;
    @JsonProperty("token")
    private String authToken;

    public LoginResponse() {
    }

    public LoginResponse(UUID id, String email, String authToken) {
        this.id = id;
        this.email = email;
        this.authToken = authToken;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
