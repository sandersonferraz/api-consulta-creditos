package com.desafio.credito_api.domain.service;

import com.desafio.credito_api.web.dto.CreditoResponseDTO;

import java.util.List;

public interface CreditoConsultaService {

    List<CreditoResponseDTO> buscarCreditosPorNumeroNfse(String numeroNfse);
    CreditoResponseDTO buscarCreditoPorNumeroCredito(String numeroCredito);
}
