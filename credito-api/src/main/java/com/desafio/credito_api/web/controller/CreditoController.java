package com.desafio.credito_api.web.controller;

import com.desafio.credito_api.domain.service.CreditoConsultaService;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/creditos")
public class CreditoController {

    private final CreditoConsultaService service;

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoResponseDTO>> listarPorNumeroNfse( @PathVariable("numeroNfse") @NotBlank String numeroNfse) {
        return ResponseEntity.ok().body(service.buscarCreditosPorNumeroNfse(numeroNfse));
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoResponseDTO> obterPorNumeroCredito( @PathVariable("numeroCredito") @NotBlank String numeroCredito) {
        return ResponseEntity.ok().body(service.buscarCreditoPorNumeroCredito(numeroCredito));
    }
}
