package com.desafio.credito_api.web.dto;

public record CreditoAuditEventDTO(
        String numeroCredito,
        String numeroNfse
) {}
