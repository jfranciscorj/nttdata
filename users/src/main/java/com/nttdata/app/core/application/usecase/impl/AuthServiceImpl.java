package com.nttdata.app.core.application.usecase.impl;

import com.nttdata.app.api.dto.request.auth.LoginRequest;
import com.nttdata.app.api.dto.response.auth.LoginResponse;
import com.nttdata.app.api.exception.ServiceException;
import com.nttdata.app.core.application.usecase.AuthUserCase;
import com.nttdata.app.core.domain.user.UserModel;
import com.nttdata.app.core.port.UserRepositoryPort;
import com.nttdata.app.core.util.ApiConstans;
import com.nttdata.app.infrastructure.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthUserCase {

    private final UserRepositoryPort userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepositoryPort userRepository,
                            JwtUtil jwtUtil,
                            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponse authLogin(LoginRequest request) {

        UserModel user = userRepository.findByEmail(request.getEmail()).get();

        if (user == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, ApiConstans.USER_NOT_FOUND);
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, ApiConstans.INVALID_LOGIN);
        }

        if (!user.isActive()) {
            throw new ServiceException(HttpStatus.FORBIDDEN, ApiConstans.USER_INACTIVE);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        LocalDateTime now = LocalDateTime.now();
        user.setLastLogin(now);
        user.setToken(token);
        userRepository.save(user);

        return new LoginResponse(user.getId(), user.getEmail(), user.getToken());
    }

}
