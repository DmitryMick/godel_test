package org.dmitry.moviesearcher.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@JsonIgnoreProperties
public class ErrorResponse {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private List<ErrorEntity> errors;

    private ErrorResponse() {
        timestamp = LocalDateTime.now();
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(HttpStatus status) {
        this();
        this.message = "Unexpected error";
        this.status = status;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, List<ErrorEntity> errors) {
        this(status);
        this.errors = errors;
    }
}