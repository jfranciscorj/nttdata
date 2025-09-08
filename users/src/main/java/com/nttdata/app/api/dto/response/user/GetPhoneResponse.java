package com.nttdata.app.api.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.app.core.domain.user.PhoneModel;

public class GetPhoneResponse {

    @JsonProperty("numero")
    private String number;

    @JsonProperty("codigoCiudad")
    private String cityCode;

    @JsonProperty("codigoPais")
    private String countryCode;

    public static GetPhoneResponse fromDomain(PhoneModel phone) {
        GetPhoneResponse res = new GetPhoneResponse();
        res.number = phone.getNumber();
        res.cityCode = phone.getCityCode();
        res.countryCode = phone.getCountryCode();

        return res;
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
