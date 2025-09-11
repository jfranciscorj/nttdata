package com.nttdata.app.infrastructure.persistence.entity;

import com.nttdata.app.core.domain.user.PhoneModel;
import com.nttdata.app.core.domain.user.UserModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testFromDomain_withPhones() {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        PhoneModel phone = new PhoneModel("12345678", "9", "56");
        UserModel userModel = new UserModel();
        userModel.setId(userId);
        userModel.setName("Juan Perez");
        userModel.setEmail("juan@test.com");
        userModel.setPassword("Password123*");
        userModel.setCreateAt(now);
        userModel.setModifiedAt(now);
        userModel.setLastLogin(now);
        userModel.setActive(true);
        userModel.setToken("fake-token");
        userModel.setPhones(List.of(phone));

        UserEntity entity = UserEntity.fromDomain(userModel);

        assertNotNull(entity);
        assertEquals(userId, entity.getId());
        assertEquals("Juan Perez", entity.getName());
        assertEquals("juan@test.com", entity.getEmail());
        assertEquals("Password123*", entity.getPassword());
        assertEquals("fake-token", entity.getToken());
        assertTrue(entity.isActive());
        assertEquals(1, entity.getPhones().size());
        assertEquals(entity, entity.getPhones().get(0).getUser()); // relación user → phone
    }

    @Test
    void testFromDomain_withNullPhones() {
        UserModel userModel = new UserModel();
        userModel.setId(UUID.randomUUID());
        userModel.setName("Maria");
        userModel.setEmail("maria@test.com");
        userModel.setPassword("Password123*");

        UserEntity entity = UserEntity.fromDomain(userModel);

        assertNotNull(entity);
        assertNotNull(entity.getPhones());
        assertTrue(entity.getPhones().isEmpty());
    }

    @Test
    void testToDomain_withPhones() {
        UUID userId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        PhoneEntity phoneEntity = new PhoneEntity();
        phoneEntity.setId(1L);
        phoneEntity.setNumber("12345678");
        phoneEntity.setCityCode("9");
        phoneEntity.setCountryCode("56");

        UserEntity entity = new UserEntity();
        entity.setId(userId);
        entity.setName("Pedro");
        entity.setEmail("pedro@test.com");
        entity.setPassword("Password123*");
        entity.setCreateAt(now);
        entity.setModifiedAt(now);
        entity.setLastLogin(now);
        entity.setActive(true);
        entity.setToken("token-xyz");
        entity.setPhones(List.of(phoneEntity));

        // Vincular relación inversa
        phoneEntity.setUser(entity);

        UserModel model = entity.toDomain();

        assertNotNull(model);
        assertEquals(userId, model.getId());
        assertEquals("Pedro", model.getName());
        assertEquals("pedro@test.com", model.getEmail());
        assertEquals("Password123*", model.getPassword());
        assertEquals("token-xyz", model.getToken());
        assertTrue(model.isActive());
        assertEquals(1, model.getPhones().size());
        assertEquals("12345678", model.getPhones().get(0).getNumber());
    }

    @Test
    void testToDomain_withEmptyPhones() {
        UserEntity entity = new UserEntity();
        entity.setId(UUID.randomUUID());
        entity.setName("Test User");
        entity.setEmail("test@test.com");
        entity.setPassword("Password123*");
        entity.setPhones(List.of()); // lista vacía

        UserModel model = entity.toDomain();

        assertNotNull(model);
        assertNotNull(model.getPhones());
        assertTrue(model.getPhones().isEmpty());
    }
}
