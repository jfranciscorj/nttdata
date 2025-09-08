package com.nttdata.app.core.application.usecase;

import com.nttdata.app.api.dto.request.auth.LoginRequest;
import com.nttdata.app.api.dto.response.auth.LoginResponse;

public interface AuthUserCase {
    LoginResponse authLogin(LoginRequest request);
}
