package com.desafio.credito_api.domain.repository;

import com.desafio.credito_api.domain.model.Credito;
import com.desafio.credito_api.infrastructure.repository.CreditoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditoRepositoryTest {

    @Mock
    private CreditoRepository repository;

    @Test
    void DeveRetornarListaDeCreditoContituidoPorNumeroDaNfse() {

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
        List<Credito> output = repository.findByNumeroNfse(input);
        assertThat(output).isNotEmpty();
        assertThat(output).hasSize(2);

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
        assertThat(output).isEmpty();
        assertThat(output).hasSize(0);
    }

    @Test
    @DisplayName("Deve retorna os detalhes de um crédito constituído específico com base no número\n" +
            "do crédito constituído")
    void DeveRetornarDetalhesDeUmCreditoConstituidoPorNumeroDoCredito() {
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
        Optional<Credito> output = repository.findByNumeroCredito(input);
        assertThat(output).isPresent();
        assertThat(output.get().getNumeroCredito()).isEqualTo("654321");
        assertThat(output.get().getNumeroNfse()).isEqualTo("1122334");
        assertThat(output.get().getDataConstituicao()).isEqualTo(LocalDate.of(2024, 1, 15));
        assertThat(output.get().getValorIssqn()).isEqualTo(new BigDecimal("800.50"));
        assertThat(output.get().getTipoCredito()).isEqualTo("Outros");
        assertThat(output.get().isSimplesNacional()).isTrue();
        assertThat(output.get().getAliquota()).isEqualTo(new BigDecimal("3.5"));
        assertThat(output.get().getValorFaturado()).isEqualTo(new BigDecimal("20000.00"));
        assertThat(output.get().getValorDeducao()).isEqualTo(new BigDecimal("3000.00"));
        assertThat(output.get().getBaseCalculo()).isEqualTo(new BigDecimal("17000.00"));
    }

    @Test
    void DeveRetornarNenhumDetalhesDeUmCreditoConstituidoPorNumeroDoCredito() {
        when(repository.findByNumeroCredito("654321")).thenReturn(Optional.empty());
        String input = "654321";
        Optional<Credito> output = repository.findByNumeroCredito(input);
        assertThat(output).isNotPresent();
    }
}
