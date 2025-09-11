package com.nttdata.app.api.controller;

import com.nttdata.app.api.dto.request.user.CreateUserRequest;
import com.nttdata.app.api.dto.request.user.UpdateUserRequest;
import com.nttdata.app.api.dto.response.user.CreateUserResponse;
import com.nttdata.app.api.dto.response.user.GetStatusResponse;
import com.nttdata.app.api.dto.response.user.GetUserResponse;
import com.nttdata.app.core.application.usecase.CreateUserUseCase;
import com.nttdata.app.core.application.usecase.DeleteUserUseCase;
import com.nttdata.app.core.application.usecase.GetUserUseCase;
import com.nttdata.app.core.application.usecase.UpdateUserUseCase;
import com.nttdata.app.core.domain.user.UserModel;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, DeleteUserUseCase deleteUserUseCase, GetUserUseCase getUserUseCase, UpdateUserUseCase updateUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateUserResponse> create(@RequestBody CreateUserRequest request) {

        logger.info("Start Create User");

        UserModel created = createUserUseCase.createUserUseCase(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(CreateUserResponse.fromDomain(created));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetUserResponse>> getAllUser(@RequestHeader("Authorization") String token){
        System.out.println("Controller Get All User");
        List<UserModel> users = getUserUseCase.getAllUserUserCase();

        List<GetUserResponse> response = users.stream()
                .map(GetUserResponse::fromDomain)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponse> getById(@PathVariable(name="id") UUID id,
                                                   @RequestHeader("Authorization") String token) {
        System.out.println("Controller Get By Id");
        Optional<UserModel> user = getUserUseCase.getUserByIdUserCase(id);

        return user.map(u -> ResponseEntity.ok(GetUserResponse.fromDomain(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponse> updateUser(
            @PathVariable UUID id,
            @RequestBody UpdateUserRequest request,
            @RequestHeader("Authorization") String token) {
        System.out.println("Controller Update By Id");
        UserModel updated = updateUserUseCase.updateUserUseCase(id, request.toDomain());
        return ResponseEntity.ok(GetUserResponse.fromDomain(updated));
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponse> partialUpdateUser(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> fields,
            @RequestHeader("Authorization") String token) {
        System.out.println("Controller Update Partial By Id");
        UserModel updated = updateUserUseCase.updatePartialUserCase(id, fields);
        return ResponseEntity.ok(GetUserResponse.fromDomain(updated));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteById(@PathVariable(name="id") UUID id,
                                                      @RequestHeader("Authorization") String token) {
        System.out.println("Controller Delete By Id");
        deleteUserUseCase.deleteUserUseCase(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{id}/enable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponse> enableUserStatus(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String token) {
        System.out.println("Controller Enable User Status");
        UserModel updated = updateUserUseCase.enableStatusUserCase(id);
        return ResponseEntity.ok(GetUserResponse.fromDomain(updated));
    }

    @PatchMapping(value = "/{id}/disable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetUserResponse> disableUserStatus(
            @PathVariable UUID id) {
        System.out.println("Controller Disable User Status");
        UserModel updated = updateUserUseCase.disableStatusUserCase(id);
        return ResponseEntity.ok(GetUserResponse.fromDomain(updated));
    }

    @GetMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetStatusResponse> getStatus(@PathVariable(name="id") UUID id,
                                                       @RequestHeader("Authorization") String token) {
        System.out.println("Controller Get User Status");
        GetStatusResponse status = new GetStatusResponse();
        Optional<UserModel> user = getUserUseCase.getStatus(id);
        if(user.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        status.setActive(user.get().isActive());
        return ResponseEntity.ok().body(status);
    }
}
