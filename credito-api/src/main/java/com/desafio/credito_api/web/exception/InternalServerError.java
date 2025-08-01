package com.desafio.credito_api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends BusinessException{
    public InternalServerError(String message) {
        super(message);
    }
}
