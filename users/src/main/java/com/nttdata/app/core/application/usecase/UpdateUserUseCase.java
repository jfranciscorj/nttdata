package com.nttdata.app.core.application.usecase;

import com.nttdata.app.core.domain.user.UserModel;

import java.util.Map;
import java.util.UUID;

public interface UpdateUserUseCase {
    UserModel updatePartialUserCase(UUID id, Map<String, Object> fields);
    UserModel updateUserUseCase(UUID id, UserModel request);
    UserModel enableStatusUserCase(UUID id);
    UserModel disableStatusUserCase(UUID id);
}
