package com.desafio.credito_api.application.usecase;

import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.domain.usecase.BuscarCreditosPorNumeroNfseUseCase;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import com.desafio.credito_api.web.mapper.CreditoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuscarCreditosPorNumeroNfseUseCaseImpl implements BuscarCreditosPorNumeroNfseUseCase {

    private final CreditoRepository repository;

    @Override
    public List<CreditoResponseDTO> executar(String numeroNfse) {
        List<Credito> creditoList = repository.findByNumeroNfse(numeroNfse);
        if (creditoList.isEmpty()) throw new RuntimeException("Nenhum crédito encontrado para a nota fiscal: " + numeroNfse);
        return CreditoMapper.INSTANCE.toResponseList(creditoList);
    }
}
