package it.epicode.w6d4.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class ErrorModel {
    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;

    public ErrorModel(int statusCode, String error, String message) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        timestamp = LocalDateTime.now();
    }
}
