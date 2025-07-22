package com.desafio.credito_api.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionDetails.builder()
                        .title("Recurso não encontrado")
                        .code(HttpStatus.NOT_FOUND.value())
                        .cause(ex.getCause() != null ? ex.getCause().getClass().getSimpleName() : ex.getClass().getSimpleName())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDetails.builder()
                        .title("Bad Request")
                        .code(HttpStatus.BAD_REQUEST.value())
                        .cause(ex.getCause().getMessage())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<?> handleInternalServerError(InternalServerError ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDetails.builder()
                        .title("Internal Server Error")
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .cause(ex.getCause().getMessage())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .build());
    }

}
