package com.desafio.credito_api.infrastructure.kafka;

import com.desafio.credito_api.infrastructure.repository.CreditoAuditLogRepository;
import com.desafio.credito_api.web.dto.CreditoAuditEventDTO;
import com.desafio.credito_api.web.mapper.KafkaMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaAuditConsumer {

    private final CreditoAuditLogRepository repository;

    public KafkaAuditConsumer(CreditoAuditLogRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "credit-events", groupId = "audit")
    public void consume(CreditoAuditEventDTO dto) {
        try {
            repository.save(KafkaMapper.INSTANCE.toEntity(dto));
        } catch (Exception e) {
            System.err.println("Kafka auditória erro: " + e.getMessage());
        }
    }
}
