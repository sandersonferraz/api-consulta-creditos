package com.desafio.credito_api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionDetails.builder()
                        .title("NOT FOUND")
                        .code(HttpStatus.NOT_FOUND.value())
                        .cause(ex.getCause().getMessage())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(ex.getLocalizedMessage())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDetails.builder()
                        .title("Bad Request")
                        .code(HttpStatus.BAD_REQUEST.value())
                        .cause(ex.getCause().getMessage())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(ex.getLocalizedMessage())
                        .build());
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<?> handleInternalServerError(InternalServerError ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDetails.builder()
                        .title("Internal Server Error")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .cause(ex.getCause().getMessage())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(ex.getLocalizedMessage())
                        .build());
    }

}
