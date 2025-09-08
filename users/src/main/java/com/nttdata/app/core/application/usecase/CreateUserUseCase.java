package com.nttdata.app.core.application.usecase;

import com.nttdata.app.core.domain.user.UserModel;

public interface CreateUserUseCase {
    UserModel createUserUseCase(UserModel user);
}
