package com.nttdata.app.api.exception;

import com.nttdata.app.api.dto.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedExceptions(HttpRequestMethodNotSupportedException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        error.setMessage("Method not allowed.");
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<ErrorResponse> errors = new ArrayList<>();

        result.getAllErrors().forEach(er -> {
            String field = ((FieldError) er).getField();

            String errorMessage = "'" + field + "': " + er.getDefaultMessage();

            ErrorResponse error = new ErrorResponse();
            error.setStatusCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage(errorMessage);
            error.setInfo("Error");
            error.setLevel("Funcional");
            errors.add(error);

        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(NoResourceFoundException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setMessage("Not Found.");
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MissingServletRequestParameterException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Required query parameter " + ex.getParameterName() + " not specified");
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MissingRequestHeaderException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Required header " + ex.getHeaderName() + " not specified");
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Invalid body structure.");
        error.setInfo("Error");
        error.setLevel("Funcional");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
