package com.nttdata.app.api.exception;

import com.nttdata.app.api.dto.response.error.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Campo inválido");
        var response = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse body = (ErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("Campo inválido", body.getMessage());
    }

    @Test
    void testHandleMethodNotAllowedExceptions() {
        HttpRequestMethodNotSupportedException ex =
                new HttpRequestMethodNotSupportedException("POST", List.of("GET"));
        var response = handler.handleMethodNotAllowedExceptions(ex);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("Method not allowed.", response.getBody().getMessage());
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Generic error");
        var response = handler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Generic error", response.getBody().getMessage());
    }

    @Test
    void testHandleValidationExceptions_MethodArgumentNotValid() {
        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(new Object(), "user");
        bindingResult.addError(new FieldError("user", "email", "Invalid email"));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        var response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getMessage().contains("email"));
    }

    @Test
    void testHandleValidationExceptions_NoResourceFound() {
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/invalid");
        var response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not Found.", response.getBody().getMessage());
    }

    @Test
    void testHandleValidationExceptions_MissingServletRequestParameter() {
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("param1", "String");

        var response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("param1"));
    }

    @Test
    void testHandleValidationExceptions_MissingRequestHeader() {
        MissingRequestHeaderException ex =
                new MissingRequestHeaderException("Authorization", null);

        var response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Authorization"));
    }

    @Test
    void testHandleValidationExceptions_HttpMessageNotReadable() {
        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("Malformed JSON", (Throwable) null);

        var response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid body structure.", response.getBody().getMessage());
    }
}
