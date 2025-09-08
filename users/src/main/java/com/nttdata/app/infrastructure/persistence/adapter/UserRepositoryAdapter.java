package com.nttdata.app.infrastructure.persistence.adapter;

import com.nttdata.app.core.port.UserRepositoryPort;
import com.nttdata.app.core.domain.user.UserModel;
import com.nttdata.app.infrastructure.persistence.entity.UserEntity;
import com.nttdata.app.infrastructure.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository repository;

    public UserRepositoryAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserModel save(UserModel user) {
        UserEntity entity = UserEntity.fromDomain(user);
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        return repository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return repository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public List<UserModel> findAll() {
        return repository.findAll().stream()
                .map(UserEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
