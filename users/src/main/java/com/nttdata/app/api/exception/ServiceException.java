package com.nttdata.app.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class ServiceException extends RuntimeException{

    private HttpStatus code;
    private Error error;

    /**
     * timestamp
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * @param code
     * @param message
     */
    public ServiceException(HttpStatus code, String message) {
        super(message);
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public ServiceException(HttpStatus httpStatus, Error errorDTO) {
        super(errorDTO.getMessage());
        this.code = httpStatus;
        this.error = errorDTO;
    }
}