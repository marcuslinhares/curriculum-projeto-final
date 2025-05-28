package dev.marcus.curriculum.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.SituacaoCountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Candidatos")
public interface CandidatoController {
    @Operation(
        summary = "Cadastra novo candidato no sistema.",
        responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "409", ref = "conflict"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    @SecurityRequirement(name = "bearerKey")
    ResponseEntity<ResRegistroCandidatoDTO> save(
        ReqRegistroCandidatoDTO dto,
        UriComponentsBuilder uriBuilder
    );

    @Operation(
        summary = "Aprova ou Reprova um candidato.",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "403", ref = "forbidden"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    @SecurityRequirement(name = "bearerKey")
    ResponseEntity<ResRegistroCandidatoDTO> aprovaReprovaCandidato(
        UUID candidatoId, boolean aprovado
    );

    @Operation(
        summary = "retorna relação de situação e quantidade dos candidatos.",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "403", ref = "forbidden"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    @SecurityRequirement(name = "bearerKey")
    ResponseEntity<List<SituacaoCountDTO>> getSituacaoCounts();
}
