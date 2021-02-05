package org.dmitry.moviesearcher.exceptionhandler;

import org.dmitry.moviesearcher.exceptionhandler.exception.InvalidDataRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionsHandler {

    @ExceptionHandler(value = InvalidDataRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(InvalidDataRequestException exception) {
        List<ErrorEntity> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(err -> new ErrorEntity(err.getField(), err.getRejectedValue(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(HttpStatus.BAD_REQUEST, errors);
    }
}
