package com.nttdata.app.api.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetStatusResponse {

    @JsonProperty("activo")
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
