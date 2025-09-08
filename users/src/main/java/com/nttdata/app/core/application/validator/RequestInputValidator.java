package com.nttdata.app.core.application.validator;

import com.nttdata.app.core.domain.user.UserModel;
import org.springframework.stereotype.Component;

@Component
public class RequestInputValidator {

    final RegexUtils regexUtils;

    public RequestInputValidator(RegexUtils regexUtils) {
        this.regexUtils = regexUtils;
    }

    public void validateFieldIsEmpty(String fieldValue, String fieldName) {
        System.out.println("campo: " + fieldName + ", valor: " + fieldValue);
        if (fieldValue == null || fieldValue.trim().isEmpty()) {
            throw new IllegalArgumentException("El "+ fieldName.toLowerCase() + " no puede estar vacío.");
        }
    }

    public void validateNombreFormat(String nombre){
        regexUtils.validateRegex(RegexTypes.NOMBRE_FORMAT, nombre, "nombre");
    }

    public void validateEmailFormat(String correo) {
        regexUtils.validateRegex(RegexTypes.EMAIL, correo, "correo");

    }

    public void validateClave(String clave) {
        regexUtils.validateRegex(RegexTypes.PASSWORD_FORMAT, clave, "clave");
    }

    public void validatorTelefono(UserModel user) {
        user.getPhones().forEach(phone -> {

            System.out.println("numero: " + phone.getNumber()+ ", ciudad: " + phone.getCityCode() + ", pais: " + phone.getCountryCode());

            if (phone.getNumber() == null) {
                throw new IllegalArgumentException("El campo numero no puede estar vacío.");
            }

            validateOnlyNumber(phone.getNumber(), "numero");

            if (phone.getCityCode() == null) {
                throw new IllegalArgumentException("El campo codigo_ciudad no puede estar vacío.");
            }

            validateOnlyNumber(phone.getCityCode(), "codigo_ciudad");

            if (phone.getCountryCode() == null) {
                throw new IllegalArgumentException("El campo codigo_pais no puede estar vacío.");
            }

            validateOnlyNumber(phone.getCountryCode(), "codigo_pais");
        });
    }

    public void validateOnlyNumber(String fieldValue, String fieldName){
        regexUtils.validateRegex(RegexTypes.ONLY_NUMBERS, fieldValue, fieldName);
    }

}
