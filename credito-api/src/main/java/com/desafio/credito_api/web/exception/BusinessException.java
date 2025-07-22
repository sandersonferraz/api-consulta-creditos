package com.desafio.credito_api.web.exception;

public class BusinessException  extends RuntimeException {
    public BusinessException(String message){ super(message);}
    public BusinessException(String messsage, Throwable cause) {
        super(messsage, cause);
    }
}
