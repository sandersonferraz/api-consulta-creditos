package com.desafio.credito_api.domain.usecase;

import com.desafio.credito_api.application.usecase.BuscarCreditoPorNumeroCreditoUseCaseImpl;
import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.infrastructure.kafka.KafkaProducer;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import com.desafio.credito_api.web.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarCreditoPorNumeroCreditoTest {

    @Mock
    private CreditoRepository repository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private BuscarCreditoPorNumeroCreditoUseCaseImpl useCase;

    @Test
    void deveRetornarCreditoContituidoPorNumeroDeCredito() {
        Credito credito = Credito.builder()
                .numeroCredito("654321")
                .numeroNfse("1122334")
                .dataConstituicao(LocalDate.of(2024, 1, 15))
                .valorIssqn(new BigDecimal("800.50"))
                .tipoCredito("Outros")
                .simplesNacional(true)
                .aliquota(new BigDecimal("3.5"))
                .valorFaturado(new BigDecimal("20000.00"))
                .valorDeducao(new BigDecimal("3000.00"))
                .baseCalculo(new BigDecimal("17000.00"))
                .build();
        when(repository.findByNumeroCredito("654321")).thenReturn(Optional.of(credito));
        String input = "654321";

        CreditoResponseDTO output = useCase.executar(input);
        assertThat(output).isNotNull();

        assertThat(output.getNumeroCredito()).isEqualTo("654321");
        assertThat(output.getNumeroNfse()).isEqualTo("1122334");
        assertThat(output.getDataConstituicao()).isEqualTo(LocalDate.of(2024, 1, 15));
        assertThat(output.getValorIssqn()).isEqualTo(new BigDecimal("800.50"));
        assertThat(output.getTipoCredito()).isEqualTo("Outros");
        assertThat(output.isSimplesNacional()).isTrue();
        assertThat(output.getAliquota()).isEqualTo(new BigDecimal("3.5"));
        assertThat(output.getValorFaturado()).isEqualTo(new BigDecimal("20000.00"));
        assertThat(output.getValorDeducao()).isEqualTo(new BigDecimal("3000.00"));
        assertThat(output.getBaseCalculo()).isEqualTo(new BigDecimal("17000.00"));

    }

    @Test
    void deveRetornarExceptionNotFound() {
        String input = "00000";
        when(repository.findByNumeroCredito(input)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> useCase.executar(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Crédito constituído não encontrado: " + input);
    }


}
