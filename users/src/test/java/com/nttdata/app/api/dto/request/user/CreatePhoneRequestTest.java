package com.nttdata.app.api.dto.request.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatePhoneRequestTest {

    @Test
    void test_getter_setter(){

        CreatePhoneRequest request = new CreatePhoneRequest();

        request.setNumber("50263838");
        request.setCityCode("9");
        request.setCountryCode("56");

        assertEquals("50263838", request.getNumber());
        assertEquals("9", request.getCityCode());
        assertEquals("56", request.getCountryCode());

    }

}