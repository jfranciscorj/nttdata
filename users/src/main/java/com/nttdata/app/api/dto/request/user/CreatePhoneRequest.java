package com.nttdata.app.api.dto.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.PhoneModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class CreatePhoneRequest {

    @NotBlank(message = "The number is mandatory.")
    @JsonProperty("numero")
    private String number;

    @NotBlank(message = "The codigoCiudad is mandatory.")
    @JsonProperty("codigoCiudad")
    private String cityCode;

    @NotBlank(message = "The codigoPais is mandatory.")
    @JsonProperty("codigoPais")
    private String countryCode;

    public PhoneModel toDomain() {
        return new PhoneModel(number, cityCode, countryCode);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
