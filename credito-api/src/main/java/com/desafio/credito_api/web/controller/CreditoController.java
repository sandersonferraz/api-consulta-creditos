package com.desafio.credito_api.web.controller;

import com.desafio.credito_api.domain.service.CreditoConsultaService;
import com.desafio.credito_api.web.dto.CreditoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Buscar créditos por número da NFS-e",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Créditos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CreditoResponseDTO.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nenhum crédito encontrado",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    public ResponseEntity<List<CreditoResponseDTO>> listarPorNumeroNfse(
            @Parameter(
                    description = "Número identificador da Nota Fiscal de Serviço Eletrônica (NFS-e)",
                    example = "7891011"
            )
            @PathVariable("numeroNfse") @NotBlank String numeroNfse) {
        return ResponseEntity.ok().body(service.buscarCreditosPorNumeroNfse(numeroNfse));
    }

    @GetMapping("/credito/{numeroCredito}")
    @Operation(
            summary = "Buscar créditos por número de crédito",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Créditos encontrados"),
                    @ApiResponse(responseCode = "404", description = "Nenhum crédito encontrado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    public ResponseEntity<CreditoResponseDTO> obterPorNumeroCredito(
            @Parameter(
                    description = "Número identificador da Nota Fiscal de Serviço Eletrônica (NFS-e)",
                    example = "7891011"
            )

            @PathVariable("numeroCredito") @NotBlank String numeroCredito) {
        return ResponseEntity.ok().body(service.buscarCreditoPorNumeroCredito(numeroCredito));
    }
}
