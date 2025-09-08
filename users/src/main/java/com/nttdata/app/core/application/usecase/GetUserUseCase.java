package com.nttdata.app.core.application.usecase;

import com.nttdata.app.core.domain.user.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GetUserUseCase {
    List<UserModel> getAllUserUserCase();
    Optional<UserModel> getUserByIdUserCase(UUID id);
    Boolean getStatus(UUID id);
}
