package com.nttdata.app.core.application.usecase.impl;

import com.nttdata.app.api.controller.UserController;
import com.nttdata.app.core.application.usecase.CreateUserUseCase;
import com.nttdata.app.core.application.usecase.DeleteUserUseCase;
import com.nttdata.app.core.application.usecase.GetUserUseCase;
import com.nttdata.app.core.application.usecase.UpdateUserUseCase;
import com.nttdata.app.core.application.validator.RequestInputValidator;
import com.nttdata.app.core.domain.user.UserModel;
import com.nttdata.app.core.port.UserRepositoryPort;
import com.nttdata.app.infrastructure.security.JwtUtil;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements CreateUserUseCase, DeleteUserUseCase, GetUserUseCase, UpdateUserUseCase {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepositoryPort userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RequestInputValidator validator;

    public UserServiceImpl(UserRepositoryPort userRepository,
                           JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder,
                           RequestInputValidator validator) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @Override
    public UserModel createUserUseCase(UserModel user) {

        validator.validateFieldIsEmpty(user.getName(), "nombre");
        validator.validateNombreFormat(user.getName());
        validator.validateFieldIsEmpty(user.getEmail(), "correo");
        validator.validateEmailFormat(user.getEmail());

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        validator.validateFieldIsEmpty(user.getPassword(), "clave");
        validator.validateClave(user.getPassword());

        if(!user.getPhones().isEmpty()){
            validator.validatorTelefono(user);
        }

        String token = jwtUtil.generateToken(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setToken(token);

        return userRepository.save(user);
    }

    @Override
    public void deleteUserUseCase(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserModel> getAllUserUserCase() {

        List<UserModel> users = userRepository.findAll();

        return users;
    }

    @Override
    public Optional<UserModel> getUserByIdUserCase(UUID id) {

        Optional<UserModel> user = userRepository.findById(id);

        return user;
    }

    @Override
    public UserModel updatePartialUserCase(UUID id, Map<String, Object> fields) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Boolean update = false;

        if (fields.containsKey("nombre")) {
            update = true;
            validator.validateFieldIsEmpty((String) fields.get("nombre"), "nombre");
            validator.validateNombreFormat((String) fields.get("nombre"));
            user.setName((String) fields.get("nombre"));
        }
        if (fields.containsKey("correo")) {
            update = true;

//            if(!user.getEmail().equals(fields.containsKey("correo"))){
//                if (userRepository.findByEmail((String) fields.get("correo")).isPresent()) {
//                    throw new IllegalArgumentException("El correo ya está registrado");
//                }
//            }

//            validator.validateFieldIsEmpty((String) fields.get("correo"), "correo");
//            validator.validateEmailFormat((String) fields.get("correo"));
//            user.setEmail((String) fields.get("correo"));
        }
        if (fields.containsKey("clave")) {
            update = true;
            validator.validateFieldIsEmpty((String) fields.get("clave"), "clave");
            validator.validateClave((String) fields.get("clave"));
            user.setPassword(passwordEncoder.encode((String) fields.get("clave")));
        }

        if(update){
            LocalDateTime now = LocalDateTime.now();
            user.setModifiedAt(now);
        }

        return userRepository.save(user);
    }

    @Override
    public UserModel updateUserUseCase(UUID id, UserModel request) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        validator.validateFieldIsEmpty(request.getName(), "nombre");
        validator.validateNombreFormat(request.getName());
        user.setName(request.getName());

//        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("El correo ya está registrado");
//        }

//        validator.validateFieldIsEmpty(request.getEmail(), "correo");
//        validator.validateEmailFormat(request.getEmail());
//        user.setEmail(request.getEmail());

        validator.validateFieldIsEmpty(request.getPassword(), "contraseña");
        validator.validateClave(request.getPassword());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if(!user.getPhones().isEmpty()){
            validator.validatorTelefono(user);
        }
        user.setPhones(request.getPhones());
//        user.setActive(request.isActive());

        return userRepository.save(user);
    }

    @Override
    public Optional<UserModel> getStatus(UUID id) {

        Optional<UserModel> user = userRepository.findById(id);

        return user;
    }

    @Override
    public UserModel disableStatusUserCase(UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setActive(false);

        LocalDateTime now = LocalDateTime.now();
        user.setModifiedAt(now);

        return userRepository.save(user);
    }

    @Override
    public UserModel enableStatusUserCase(UUID id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setActive(true);

        LocalDateTime now = LocalDateTime.now();
        user.setModifiedAt(now);

        return userRepository.save(user);
    }

}
