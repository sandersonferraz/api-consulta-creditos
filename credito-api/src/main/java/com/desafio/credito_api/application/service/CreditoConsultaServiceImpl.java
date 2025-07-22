package com.desafio.credito_api.application.service;

import com.desafio.credito_api.domain.service.CreditoConsultaService;
import com.desafio.credito_api.domain.usecase.BuscarCreditoPorNumeroCreditoUseCase;
import com.desafio.credito_api.domain.usecase.BuscarCreditosPorNumeroNfseUseCase;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditoConsultaServiceImpl implements CreditoConsultaService {

    private final BuscarCreditosPorNumeroNfseUseCase buscarCreditosPorNumeroNfseUseCase;

    private final BuscarCreditoPorNumeroCreditoUseCase buscarCreditoPorNumeroCreditoUseCase;

    @Override
    public List<CreditoResponseDTO> buscarCreditosPorNumeroNfse(String numeroNfse) {
        return buscarCreditosPorNumeroNfseUseCase.executar(numeroNfse);
    }

    @Override
    public CreditoResponseDTO buscarCreditoPorNumeroCredito(String numeroCredito) {
        return buscarCreditoPorNumeroCreditoUseCase.executar(numeroCredito);
    }
}
