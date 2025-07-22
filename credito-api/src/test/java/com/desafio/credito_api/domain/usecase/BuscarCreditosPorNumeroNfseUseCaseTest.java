package com.desafio.credito_api.domain.usecase;

import com.desafio.credito_api.application.usecase.BuscarCreditosPorNumeroNfseUseCaseImpl;
import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuscarCreditosPorNumeroNfseUseCaseTest {

    @Mock
    private CreditoRepository repository;

    @InjectMocks
    private BuscarCreditosPorNumeroNfseUseCaseImpl useCase;

    @Test
    void deveObterListaCreditoContituidoPorNumeroNfse() {
        Credito credito1 = Credito.builder()
                .numeroCredito("123456")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 2, 25))
                .valorIssqn(new BigDecimal("1500.75"))
                .tipoCredito("ISSQN")
                .simplesNacional(true)
                .aliquota(new BigDecimal("5.0"))
                .valorFaturado(new BigDecimal("30000.00"))
                .valorDeducao(new BigDecimal("5000.00"))
                .baseCalculo(new BigDecimal("25000.00"))
                .build();

        Credito credito2 = Credito.builder()
                .numeroCredito("789012")
                .numeroNfse("7891011")
                .dataConstituicao(LocalDate.of(2024, 2, 26))
                .valorIssqn(new BigDecimal("1200.50"))
                .tipoCredito("ISSQN")
                .simplesNacional(false)
                .aliquota(new BigDecimal("4.5"))
                .valorFaturado(new BigDecimal("25000.00"))
                .valorDeducao(new BigDecimal("4000.00"))
                .baseCalculo(new BigDecimal("21000.00"))
                .build();
        when(repository.findByNumeroNfse("7891011"))
                .thenReturn(List.of(credito1, credito2));

        String input = "7891011";
        List<CreditoResponseDTO> output = useCase.executar(input);

        assertThat(output).isNotNull();

        assertThat(output.getFirst().getNumeroCredito()).isEqualTo("123456");
        assertThat(output.getFirst().getNumeroNfse()).isEqualTo("7891011");
        assertThat(output.getFirst().getTipoCredito()).isEqualTo("ISSQN");
        assertThat(output.getFirst().getDataConstituicao()).isEqualTo(LocalDate.of(2024, 2, 25));
        assertThat(output.getFirst().getValorIssqn()).isEqualTo(new BigDecimal("1500.75"));
        assertThat(output.getFirst().getTipoCredito()).isEqualTo("ISSQN");
        assertThat(output.getFirst().isSimplesNacional()).isTrue();
        assertThat(output.getFirst().getAliquota()).isEqualTo(new BigDecimal("5.0"));
        assertThat(output.getFirst().getValorFaturado()).isEqualTo(new BigDecimal("30000.00"));
        assertThat(output.getFirst().getValorDeducao()).isEqualTo(new BigDecimal("5000.00"));
        assertThat(output.getFirst().getBaseCalculo()).isEqualTo(new BigDecimal("25000.00"));


        assertThat(output.getLast().getNumeroCredito()).isEqualTo("789012");
        assertThat(output.getLast().getNumeroNfse()).isEqualTo("7891011");
        assertThat(output.getLast().getTipoCredito()).isEqualTo("ISSQN");
        assertThat(output.getLast().getDataConstituicao()).isEqualTo(LocalDate.of(2024, 2, 26));
        assertThat(output.getLast().getValorIssqn()).isEqualTo(new BigDecimal("1200.50"));
        assertThat(output.getLast().getTipoCredito()).isEqualTo("ISSQN");
        assertThat(output.getLast().isSimplesNacional()).isFalse();
        assertThat(output.getLast().getAliquota()).isEqualTo(new BigDecimal("4.5"));
        assertThat(output.getLast().getValorFaturado()).isEqualTo(new BigDecimal("25000.00"));
        assertThat(output.getLast().getValorDeducao()).isEqualTo(new BigDecimal("4000.00"));
        assertThat(output.getLast().getBaseCalculo()).isEqualTo(new BigDecimal("21000.00"));

    }

    @Test
    void DeveRetornarListaVaziaDeCreditoContituidoPorNumeroDaNfse() {
        when(repository.findByNumeroNfse("7891011"))
                .thenReturn(List.of());

        String input = "7891011";
        List<Credito> output = repository.findByNumeroNfse(input);
        assertThatThrownBy(() -> useCase.executar(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Nenhum crédito encontrado para a nota fiscal: " + input);
    }

}
