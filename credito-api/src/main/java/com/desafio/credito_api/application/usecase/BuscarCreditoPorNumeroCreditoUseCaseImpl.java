package com.desafio.credito_api.application.usecase;

import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.domain.usecase.BuscarCreditoPorNumeroCreditoUseCase;
import com.desafio.credito_api.infrastructure.kafka.KafkaProducer;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import com.desafio.credito_api.web.dto.CreditoAuditEventDTO;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import com.desafio.credito_api.web.exception.NotFoundException;
import com.desafio.credito_api.web.mapper.CreditoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuscarCreditoPorNumeroCreditoUseCaseImpl implements BuscarCreditoPorNumeroCreditoUseCase {

    private final CreditoRepository repository;
    private final KafkaProducer kafkaProducer;

    @Override
    public CreditoResponseDTO executar(String numeroCredito) {
        Credito credito = repository.findByNumeroCredito(numeroCredito)
                .orElseThrow(() -> new NotFoundException("Crédito constituído não encontrado: " + numeroCredito));
        kafkaProducer.publish(new CreditoAuditEventDTO(
                credito.getNumeroCredito(),
                credito.getNumeroNfse()
        ));
        return CreditoMapper.INSTANCE.toResponse(credito);
    }
}
