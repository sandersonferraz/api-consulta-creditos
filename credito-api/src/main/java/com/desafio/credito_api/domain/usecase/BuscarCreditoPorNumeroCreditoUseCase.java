package com.desafio.credito_api.domain.usecase;

import com.desafio.credito_api.web.dto.CreditoResponseDTO;

public interface BuscarCreditoPorNumeroCreditoUseCase {

    CreditoResponseDTO executar(String numeroCredito);

}
