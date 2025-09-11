package com.nttdata.app.api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.UserModel;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreateUserRequest {

    @NotBlank(message = "The nombre is mandatory.")
    @JsonProperty("nombre")
    private String name;

    @NotBlank(message = "The correo is mandatory.")
    @JsonProperty("correo")
    private String email;

    @NotBlank(message = "The contraseña is mandatory.")
    @JsonProperty("contraseña")
    private String password;

    @JsonProperty("telefonos")
    private List<CreatePhoneRequest> phones;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String email, String password, List<CreatePhoneRequest> phones) {
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

    public UserModel toDomain() {
        UserModel user = new UserModel(name, email, password);

        if (phones != null) {
            user.setPhones(
                    phones.stream()
                            .map(CreatePhoneRequest::toDomain)
                            .toList()
            );
        }
        return user;
    }


}
