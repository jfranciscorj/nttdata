package com.nttdata.app.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneEntityTest {

    @Test
    void testGetterSetter() {
        PhoneEntity entity = new PhoneEntity();

        entity.setId(121214L);
        entity.setNumber("50263838");
        entity.setCityCode("9");
        entity.setCountryCode("56");

        assertNotNull(entity);
        assertEquals("50263838", entity.getNumber());
        assertEquals("9", entity.getCityCode());
        assertEquals("56", entity.getCountryCode());

    }
}