package com.nttdata.app.core.application.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilsTest {

    private RegexUtils regexUtils;

    @BeforeEach
    void setUp() throws Exception {
        regexUtils = new RegexUtils();

        setField("REGEX_ONLY_NUMBERS", "\\d+");
        setField("REGEX_ONLY_NUMBERS_ERROR", "Only numbers allowed");

        setField("REGEX_EMAIL", "^[A-Za-z0-9+_.-]+@(.+)$");
        setField("REGEX_EMAIL_ERROR", "Invalid email");

        setField("REGEX_PHONE_LENGTH", "^\\d{8,12}$");
        setField("REGEX_PHONE_LENGTH_ERROR", "Invalid phone length");

        setField("REGEX_PHONE_FORMAT", "^[0-9]+$");
        setField("REGEX_PHONE_FORMAT_ERROR", "Invalid phone format");

        setField("REGEX_PASSWORD_FORMAT", "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$");
        setField("REGEX_PASSWORD_FORMAT_ERROR", "Invalid password format");

        setField("REGEX_NOMBRE_FORMAT", "^[A-Za-zÀ-ÿ\\s]+$");
        setField("REGEX_NOMBRE_FORMAT_ERROR", "Invalid name format");
    }

    private void setField(String fieldName, String value) throws Exception {
        Field field = RegexUtils.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(regexUtils, value);
    }

    @Test
    void testOnlyNumbers_Valid() {
        assertDoesNotThrow(() ->
                regexUtils.validateRegex(RegexTypes.ONLY_NUMBERS, "12345", "numero"));
    }

//    @Test
//    void testOnlyNumbers_Invalid() {
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
//                () -> regexUtils.validateRegex(RegexTypes.ONLY_NUMBERS, "12A45", "numero"));
//        assertEquals("Invalid format (must be numbers)", ex.getMessage());
//    }

    @Test
    void testEmail_Valid() {
        assertDoesNotThrow(() ->
                regexUtils.validateRegex(RegexTypes.EMAIL, "test@mail.com", "correo"));
    }

    @Test
    void testEmail_Invalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> regexUtils.validateRegex(RegexTypes.EMAIL, "bad-email", "correo"));
        assertEquals("Invalid email", ex.getMessage());
    }

    @Test
    void testPhoneLength_Valid() {
        assertDoesNotThrow(() ->
                regexUtils.validateRegex(RegexTypes.PHONE_LENGTH, "987654321", "telefono"));
    }

    @Test
    void testPhoneLength_Invalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> regexUtils.validateRegex(RegexTypes.PHONE_LENGTH, "123", "telefono"));
        assertEquals("Invalid phone length", ex.getMessage());
    }

    @Test
    void testPhoneFormat_Valid() {
        assertDoesNotThrow(() ->
                regexUtils.validateRegex(RegexTypes.PHONE_FORMAT, "987654321", "telefono"));
    }

    @Test
    void testPhoneFormat_Invalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> regexUtils.validateRegex(RegexTypes.PHONE_FORMAT, "98-765", "telefono"));
        assertEquals("Invalid phone format", ex.getMessage());
    }

    @Test
    void testPasswordFormat_Valid() {
        assertDoesNotThrow(() ->
                regexUtils.validateRegex(RegexTypes.PASSWORD_FORMAT, "Password1", "clave"));
    }

    @Test
    void testPasswordFormat_Invalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> regexUtils.validateRegex(RegexTypes.PASSWORD_FORMAT, "password", "clave"));
        assertEquals("Invalid password format", ex.getMessage());
    }

    @Test
    void testNombreFormat_Valid() {
        assertDoesNotThrow(() ->
                regexUtils.validateRegex(RegexTypes.NOMBRE_FORMAT, "Juan Francisco Ramírez", "nombre"));
    }

    @Test
    void testNombreFormat_Invalid() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> regexUtils.validateRegex(RegexTypes.NOMBRE_FORMAT, "Juan123", "nombre"));
        assertEquals("Invalid name format", ex.getMessage());
    }
}
