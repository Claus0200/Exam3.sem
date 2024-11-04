package org.example.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiException extends RuntimeException {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ApiException(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
