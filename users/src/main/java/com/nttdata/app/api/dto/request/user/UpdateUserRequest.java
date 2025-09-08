package com.nttdata.app.api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.UserModel;

import java.util.List;

public class UpdateUserRequest {

    @JsonProperty("nombre")
    private String name;
    @JsonProperty("correo")
    private String email;
    @JsonProperty("contraseña")
    private String password;
    @JsonProperty("telefonos")
    private List<CreatePhoneRequest> phones;

    public UserModel toDomain() {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        if (phones != null) {
            user.setPhones(phones.stream().map(CreatePhoneRequest::toDomain).toList());
        }
        return user;
    }

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String name, String email, String password, List<CreatePhoneRequest> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CreatePhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<CreatePhoneRequest> phones) {
        this.phones = phones;
    }
}
