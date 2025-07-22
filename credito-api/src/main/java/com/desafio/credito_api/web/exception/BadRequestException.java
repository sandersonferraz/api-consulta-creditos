package com.desafio.credito_api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException  extends BusinessException{
    public BadRequestException(String message) {
        super(message);
    }
}
