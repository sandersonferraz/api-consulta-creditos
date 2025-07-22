package com.desafio.credito_api.web.controller;

import com.desafio.credito_api.domain.service.CreditoConsultaService;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import com.desafio.credito_api.web.exception.NotFoundException;
import com.desafio.credito_api.web.exception.RestExceptionHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditoController.class)
@Import(RestExceptionHandler.class)
public class CreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditoConsultaService service;

    private final String BASE_URL = "/api/v1/creditos";

    @Test
    void deveRetornarListaDeCreditosConstituidosPorNumeroNfse() throws Exception {
        CreditoResponseDTO credito1 = CreditoResponseDTO.builder()
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

        CreditoResponseDTO credito2 = CreditoResponseDTO.builder()
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

        when(service.buscarCreditosPorNumeroNfse("7891011"))
                .thenReturn(List.of(credito1, credito2));

        mockMvc.perform(get(BASE_URL + "/7891011"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numeroNfse").value("7891011"))
                .andExpect(jsonPath("$[0].numeroCredito").value("123456"))
                .andExpect(jsonPath("$[1].numeroCredito").value("789012"));
    }

    @Test
    void deveRetornar404QuandoNumeroNfseExiste() throws Exception {
        String input = "NF404";
        when(service.buscarCreditosPorNumeroNfse(input))
                .thenThrow(new NotFoundException("Nenhum crédito encontrado para a nota fiscal: " + input));

        mockMvc.perform(get(BASE_URL + "/" + input))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nenhum crédito encontrado para a nota fiscal: " + input))
                .andExpect(jsonPath("$.cause").value("NotFoundException"))
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.path").value(BASE_URL + "/" + input));
    }

    @Test
    void deveRetornarCreditosConstituidosPorNumeroCredito() throws Exception {
        String input = "654321";
        CreditoResponseDTO credito = CreditoResponseDTO.builder()
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

        when(service.buscarCreditoPorNumeroCredito(eq(input)))
                .thenReturn(credito);

        mockMvc.perform(get(BASE_URL + "/credito/" + input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCredito").value("654321"))
                .andExpect(jsonPath("$.numeroNfse").value("1122334"))
                .andExpect(jsonPath("$.valorIssqn").value(800.50))
                .andExpect(jsonPath("$.simplesNacional").value(true))
                .andExpect(jsonPath("$.aliquota").value(3.5));
    }

}
