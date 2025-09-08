package com.nttdata.app.api.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.UserModel;

import java.util.List;
import java.util.UUID;

public class GetUserResponse {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("nombre")
    private String name;
    @JsonProperty("correo")
    private String email;
    //@JsonProperty("contraseña")
    //private String password;
    @JsonProperty("activo")
    private boolean isActive;
    @JsonProperty("telefonos")
    private List<GetPhoneResponse> phones;

    public static GetUserResponse fromDomain(UserModel user) {
        GetUserResponse res = new GetUserResponse();
        res.id = user.getId();
        res.name = user.getName();
        res.email = user.getEmail();
        res.isActive = user.isActive();
        //res.password = user.getPassword();
        res.setPhones(
                user.getPhones().stream()
                        .map(GetPhoneResponse::fromDomain)
                        .toList()
        );

        return res;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPassword() {
//        return password;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<GetPhoneResponse> getPhones() {
        return phones;
    }

    public void setPhones(List<GetPhoneResponse> phones) {
        this.phones = phones;
    }

}
