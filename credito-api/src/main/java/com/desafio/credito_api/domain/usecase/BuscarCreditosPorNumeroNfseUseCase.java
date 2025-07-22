package com.desafio.credito_api.domain.usecase;

import com.desafio.credito_api.web.dto.CreditoResponseDTO;

import java.util.List;

public interface BuscarCreditosPorNumeroNfseUseCase {
    List<CreditoResponseDTO> executar(String numeroNfse);
}
