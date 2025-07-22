package com.desafio.credito_api.web.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ExceptionDetails {
    protected String title;
    protected int code;
    protected String message;
    protected String details;
    protected LocalDateTime timestamp;
    protected String path;
    protected String cause;
}
