package com.nttdata.app.infrastructure.persistence.entity;

import com.nttdata.app.core.domain.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Column(name="create_at")
    private LocalDateTime createAt;

    @Column(name="modified_at")
    private LocalDateTime  modifiedAt;

    @Column(name="last_login")
    private LocalDateTime lastLogin;

    @Column(name="is_active")
    private boolean isActive;

    private String token;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneEntity> phones;

    public static UserEntity fromDomain(UserModel user) {

        UserEntity entity = new UserEntity();
        entity.id = user.getId();
        entity.name = user.getName();
        entity.email = user.getEmail();
        entity.password = user.getPassword();
        entity.createAt = user.getCreateAt();
        entity.modifiedAt = user.getModifiedAt();
        entity.lastLogin = user.getLastLogin();
        entity.isActive = user.isActive();
        entity.token = user.getToken();

        entity.phones = Optional.ofNullable(user.getPhones())
                .orElse(List.of())
                .stream()
                .map(phone -> {
                    PhoneEntity phoneEntity = PhoneEntity.fromDomain(phone);
                    phoneEntity.setUser(entity);
                    return phoneEntity;
                })
                .toList();

        return entity;
    }

    public UserModel toDomain() {
        UserModel model = new UserModel();
        model.setId(id);
        model.setName(name);
        model.setEmail(email);
        model.setPassword(password);
        model.setCreateAt(createAt);
        model.setModifiedAt(modifiedAt);
        model.setLastLogin(lastLogin);
        model.setToken(token);
        model.setActive(isActive);

        model.setPhones(
                phones.stream()
                        .map(PhoneEntity::toDomain)
                        .toList()
        );

        return model;
    }

}
