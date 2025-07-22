package com.desafio.credito_api.application.usecase;

import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.domain.usecase.BuscarCreditoPorNumeroCreditoUseCase;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import com.desafio.credito_api.web.mapper.CreditoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarCreditoPorNumeroCreditoUseCaseImpl implements BuscarCreditoPorNumeroCreditoUseCase {

    private final CreditoRepository repository;

    @Override
    public CreditoResponseDTO executar(String numeroCredito) {
        Credito credito = repository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new RuntimeException("Crédito constituído não encontrado: " + numeroCredito));
        return CreditoMapper.INSTANCE.toResponse(credito);
    }
}
