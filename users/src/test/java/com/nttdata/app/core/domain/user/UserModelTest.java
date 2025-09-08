package com.nttdata.app.core.domain.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserModelTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        UserModel user = new UserModel();

        UUID id = UUID.randomUUID();
        String name = "Juan Ramirez";
        String email = "juan@test.com";
        String password = "Password123*";
        boolean isActive = true;
        LocalDateTime createAt = LocalDateTime.now().minusDays(1);
        LocalDateTime modifiedAt = LocalDateTime.now();
        LocalDateTime lastLogin = LocalDateTime.now();
        String token = "jwt-token-123";

        PhoneModel phone = new PhoneModel("987654321", "9", "56");
        List<PhoneModel> phones = new ArrayList<>();
        phones.add(phone);

        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setActive(isActive);
        user.setCreateAt(createAt);
        user.setModifiedAt(modifiedAt);
        user.setLastLogin(lastLogin);
        user.setToken(token);
        user.setPhones(phones);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertTrue(user.isActive());
        assertEquals(createAt, user.getCreateAt());
        assertEquals(modifiedAt, user.getModifiedAt());
        assertEquals(lastLogin, user.getLastLogin());
        assertEquals(token, user.getToken());
        assertEquals(1, user.getPhones().size());
        assertEquals("987654321", user.getPhones().get(0).getNumber());
    }

    @Test
    void testPhonesListIsInitialized() {
        UserModel user = new UserModel();

        assertNotNull(user.getPhones());
        assertTrue(user.getPhones().isEmpty());

        PhoneModel phone = new PhoneModel("123456789", "2", "34");
        user.getPhones().add(phone);

        assertEquals(1, user.getPhones().size());
        assertEquals("123456789", user.getPhones().get(0).getNumber());
    }

    @Test
    void testOverrideFields() {
        UserModel user = new UserModel();

        user.setName("Old Name");
        user.setName("New Name");

        assertEquals("New Name", user.getName());
    }


}