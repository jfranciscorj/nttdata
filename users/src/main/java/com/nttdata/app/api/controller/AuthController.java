package com.nttdata.app.api.controller;

import com.nttdata.app.api.dto.request.auth.LoginRequest;
import com.nttdata.app.api.dto.response.auth.LoginResponse;
import com.nttdata.app.core.application.usecase.AuthUserCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthUserCase authService;

    public AuthController(AuthUserCase authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        final LoginResponse response = authService.authLogin(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
