package com.nttdata.app.api.dto.request.user;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestTest {

    @Test
    void test_getter_setter(){
        CreateUserRequest request = new CreateUserRequest();

        request.setName("Juan Francisco");
        request.setEmail("ju.ramirezj@gmail.com");
        request.setPassword("Juanfra+2");

        List<CreatePhoneRequest> telefonos = new ArrayList<>();
        CreatePhoneRequest telefono = new CreatePhoneRequest();
        telefono.setNumber("50263838");
        telefono.setCityCode("9");
        telefono.setCountryCode("56");
        telefonos.add(telefono);

        request.setPhones(telefonos);

        assertEquals("Juan Francisco", request.getName());
        assertEquals("ju.ramirezj@gmail.com", request.getEmail());
        assertEquals("Juanfra+2", request.getPassword());
        assertNotNull(request.getPhones());
    }

}