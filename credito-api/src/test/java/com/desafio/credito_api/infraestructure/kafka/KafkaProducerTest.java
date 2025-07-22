package com.desafio.credito_api.infraestructure.kafka;

import com.desafio.credito_api.infrastructure.kafka.KafkaProducer;
import com.desafio.credito_api.web.dto.CreditoAuditEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(    MockitoExtension.class)
public class KafkaProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;


    @Test
    void deveEnviarEventoDeCreditoComSucesso() {
        CreditoAuditEventDTO dto = new CreditoAuditEventDTO("90808080", "90909090");
        assertThatCode(() -> kafkaProducer.publish(dto))
                .doesNotThrowAnyException();
        verify(kafkaTemplate).send(eq("credit-events"), anyString());
    }

    @Test
    void deveLancarExcecaoQuandoFalharNaSerializacao() throws Exception {
        CreditoAuditEventDTO dto = new CreditoAuditEventDTO("123", "456");

        KafkaProducer producerComErro = new KafkaProducer(kafkaTemplate) {
            private final ObjectMapper mockMapper = mock(ObjectMapper.class);
            @Override
            public void publish(CreditoAuditEventDTO event) {
                try {
                    when(mockMapper.writeValueAsString(any())).thenThrow(new RuntimeException("Erro simulado"));
                    String message = mockMapper.writeValueAsString(event);
                    kafkaTemplate.send("credit-events", message);
                } catch (Exception e) {
                    throw new RuntimeException("Falha ao serializar o credit event.", e);
                }
            }
        };

        assertThatThrownBy(() -> producerComErro.publish(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Falha ao serializar o credit event.");
    }

    @Test
    void deveEnviarMensagemComJsonValido() throws Exception {
        CreditoAuditEventDTO dto = new CreditoAuditEventDTO("123456789", "987654321");
        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(dto);
        kafkaProducer.publish(dto);
        verify(kafkaTemplate).send(eq("credit-events"), eq(expectedJson));
    }

}
