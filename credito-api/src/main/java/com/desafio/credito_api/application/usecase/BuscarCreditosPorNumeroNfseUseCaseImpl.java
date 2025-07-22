package com.desafio.credito_api.application.usecase;

import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.domain.usecase.BuscarCreditosPorNumeroNfseUseCase;
import com.desafio.credito_api.infrastructure.kafka.KafkaProducer;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import com.desafio.credito_api.web.dto.CreditoAuditEventDTO;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import com.desafio.credito_api.web.exception.NotFoundException;
import com.desafio.credito_api.web.mapper.CreditoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuscarCreditosPorNumeroNfseUseCaseImpl implements BuscarCreditosPorNumeroNfseUseCase {

    private final CreditoRepository repository;
    private final KafkaProducer kafkaProducerConfig;

    @Override
    public List<CreditoResponseDTO> executar(String numeroNfse) {
        List<Credito> creditoList = repository.findByNumeroNfse(numeroNfse);
        if (creditoList.isEmpty()) throw new NotFoundException("Nenhum crédito encontrado para a nota fiscal: " + numeroNfse);
        String numerosDeCreditos = creditoList.stream()
                .map(Credito::getNumeroCredito)
                .collect(Collectors.joining(", "));
        kafkaProducerConfig.publish(new CreditoAuditEventDTO(
                numerosDeCreditos,
                creditoList.getFirst().getNumeroNfse()
        ));
        return CreditoMapper.INSTANCE.toResponseList(creditoList);
    }
}
