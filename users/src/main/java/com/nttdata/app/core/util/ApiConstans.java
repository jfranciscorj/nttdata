package com.nttdata.app.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstans {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String UPDATE_USER_MESSAGE = "User updated successfully.";
    public static final String USER_NOT_FOUND = "La cuenta no existe.";
    public static final String INVALID_EMAIL = "Invalid email structure, the correct format is \"aaaa@dominio.cl\"";
    public static final String INVALID_PASSWORD = "Invalid password.";
    public static final String INVALID_LOGIN = "Usuario o contraseña incorrecto.";
    public static final String INVALID_PASSWORD_STRUCTURE = "Invalid password structure.";
    public static final String USER_INACTIVE = "User is inactive.";
    public static final String USER_ALREADY_ACTIVE = "User is already active.";
    public static final String USER_ALREADY_INACTIVE = "User is already inactive.";
    public static final String EXISTING_EMAIL = "The email is already registered.";
    public static final String DELETE_USER_MESSAGE = "User deleted successfully.";
    public static final String UNAUTHORIZED_STATUS = "Unauthorized access.";
    public static final String USER_DOES_NOT_EXIST = "The user does not exist.";

}
