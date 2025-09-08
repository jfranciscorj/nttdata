package com.nttdata.app.core.port;

import com.nttdata.app.core.domain.user.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    UserModel save(UserModel user);
    Optional<UserModel> findById(UUID id);
    Optional<UserModel> findByEmail(String email);
    List<UserModel> findAll();
    void deleteById(UUID id);
}
