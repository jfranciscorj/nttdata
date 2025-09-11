package com.nttdata.app.core.application.validator;

import com.nttdata.app.core.domain.user.PhoneModel;
import com.nttdata.app.core.domain.user.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RequestInputValidatorTest {

    private RegexUtils regexUtils;
    private RequestInputValidator validator;

    @BeforeEach
    void setUp() {
        regexUtils = mock(RegexUtils.class);
        validator = new RequestInputValidator(regexUtils);
    }

    @Test
    void testValidateFieldIsEmpty_Valid() {
        assertDoesNotThrow(() -> validator.validateFieldIsEmpty("Juan", "nombre"));
    }

    @Test
    void testValidateFieldIsEmpty_Null() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validator.validateFieldIsEmpty(null, "nombre"));
        assertEquals("The nombre can't be empty.", ex.getMessage());
    }

    @Test
    void testValidateFieldIsEmpty_Blank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validator.validateFieldIsEmpty("   ", "correo"));
        assertEquals("The correo can't be empty.", ex.getMessage());
    }

    @Test
    void testValidateNombreFormat() {
        doNothing().when(regexUtils).validateRegex(RegexTypes.NOMBRE_FORMAT, "Juan", "nombre");
        validator.validateNombreFormat("Juan");
        verify(regexUtils, times(1))
                .validateRegex(RegexTypes.NOMBRE_FORMAT, "Juan", "nombre");
    }

    @Test
    void testValidateEmailFormat() {
        doNothing().when(regexUtils).validateRegex(RegexTypes.EMAIL, "test@mail.com", "correo");
        validator.validateEmailFormat("test@mail.com");
        verify(regexUtils, times(1))
                .validateRegex(RegexTypes.EMAIL, "test@mail.com", "correo");
    }

    @Test
    void testValidateClave() {
        doNothing().when(regexUtils).validateRegex(RegexTypes.PASSWORD_FORMAT, "Clave123*", "clave");
        validator.validateClave("Clave123*");
        verify(regexUtils, times(1))
                .validateRegex(RegexTypes.PASSWORD_FORMAT, "Clave123*", "clave");
    }

    @Test
    void testValidatorTelefono_Valid() {
        PhoneModel phone = new PhoneModel("987654321", "9", "56");
        UserModel user = new UserModel();
        user.setId(UUID.randomUUID());
        user.setPhones(List.of(phone));

        doNothing().when(regexUtils).validateRegex(RegexTypes.ONLY_NUMBERS, "987654321", "numero");
        doNothing().when(regexUtils).validateRegex(RegexTypes.ONLY_NUMBERS, "9", "codigo_ciudad");
        doNothing().when(regexUtils).validateRegex(RegexTypes.ONLY_NUMBERS, "56", "codigo_pais");

        assertDoesNotThrow(() -> validator.validatorTelefono(user));
        verify(regexUtils, times(1)).validateRegex(RegexTypes.ONLY_NUMBERS, "987654321", "numero");
        verify(regexUtils, times(1)).validateRegex(RegexTypes.ONLY_NUMBERS, "9", "codigo_ciudad");
        verify(regexUtils, times(1)).validateRegex(RegexTypes.ONLY_NUMBERS, "56", "codigo_pais");
    }

    @Test
    void testValidatorTelefono_NumberNull() {
        PhoneModel phone = new PhoneModel(null, "9", "56");
        UserModel user = new UserModel();
        user.setPhones(Collections.singletonList(phone));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validator.validatorTelefono(user));
        assertEquals("The numero can't be empty.", ex.getMessage());
    }

    @Test
    void testValidatorTelefono_CityCodeNull() {
        PhoneModel phone = new PhoneModel("987654321", null, "56");
        UserModel user = new UserModel();
        user.setPhones(Collections.singletonList(phone));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validator.validatorTelefono(user));
        assertEquals("The codigoCiudad can't be empty.", ex.getMessage());
    }

    @Test
    void testValidatorTelefono_CountryCodeNull() {
        PhoneModel phone = new PhoneModel("987654321", "9", null);
        UserModel user = new UserModel();
        user.setPhones(Collections.singletonList(phone));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validator.validatorTelefono(user));
        assertEquals("The codigoCiudad can't be empty.", ex.getMessage());
    }

    @Test
    void testValidateOnlyNumber() {
        doNothing().when(regexUtils).validateRegex(RegexTypes.ONLY_NUMBERS, "12345", "numero");
        validator.validateOnlyNumber("12345", "numero");
        verify(regexUtils, times(1))
                .validateRegex(RegexTypes.ONLY_NUMBERS, "12345", "numero");
    }
}
