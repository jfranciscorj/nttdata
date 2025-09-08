package com.nttdata.app.core.domain.user;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PhoneModelTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        PhoneModel phone = new PhoneModel();

        Long id = 1L;
        String number = "987654321";
        String cityCode = "9";
        String countryCode = "56";
        UUID userId = UUID.randomUUID();

        phone.setId(id);
        phone.setNumber(number);
        phone.setCityCode(cityCode);
        phone.setCountryCode(countryCode);
        phone.setUserId(userId);

        assertEquals(id, phone.getId());
        assertEquals(number, phone.getNumber());
        assertEquals(cityCode, phone.getCityCode());
        assertEquals(countryCode, phone.getCountryCode());
        assertEquals(userId, phone.getUserId());
    }

    @Test
    void testAllArgsConstructor() {
        String number = "123456789";
        String cityCode = "2";
        String countryCode = "34";

        PhoneModel phone = new PhoneModel(number, cityCode, countryCode);

        assertNull(phone.getId());
        assertEquals(number, phone.getNumber());
        assertEquals(cityCode, phone.getCityCode());
        assertEquals(countryCode, phone.getCountryCode());
        assertNull(phone.getUserId());
    }

    @Test
    void testSettersOverrideValues() {
        PhoneModel phone = new PhoneModel("111111111", "1", "11");

        phone.setNumber("222222222");
        phone.setCityCode("2");
        phone.setCountryCode("22");

        assertEquals("222222222", phone.getNumber());
        assertEquals("2", phone.getCityCode());
        assertEquals("22", phone.getCountryCode());
    }


}