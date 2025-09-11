package com.nttdata.app.api.dto.response.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetPhoneResponseTest {

    @Test
    void test_getter_setter(){
        GetPhoneResponse response = new GetPhoneResponse();

        response.setNumber("50263838");
        response.setCityCode("9");
        response.setCountryCode("56");

        assertNotNull(response);
        assertEquals("50263838", response.getNumber());
        assertEquals("9", response.getCityCode());
        assertEquals("56", response.getCountryCode());

    }

}