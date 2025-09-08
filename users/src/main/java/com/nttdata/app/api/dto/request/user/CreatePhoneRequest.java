package com.nttdata.app.api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.PhoneModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePhoneRequest {

    @NotBlank(message = "El campo numero es obligatorio.")
    @JsonProperty("numero")
    private String number;

    @NotBlank(message = "El campo codigoCiudad es obligatorio.")
    @JsonProperty("codigoCiudad")
    private String cityCode;

    @NotBlank(message = "El campo codigoPais es obligatorio.")
    @JsonProperty("codigoPais")
    private String countryCode;

    public PhoneModel toDomain() {
        return new PhoneModel(number, cityCode, countryCode);
    }
}
