package dev.marcus.curriculum.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Curriculos")
public interface CurriculoController {
    @Operation(
        summary = "Cadastra novo curriculo no sistema.",
        responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "409", ref = "conflict"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    @SecurityRequirement(name = "bearerKey")
    ResponseEntity<ResRegistroCurriculoDTO> save(
        ReqRegistroCurriculoDTO dto,
        UriComponentsBuilder uriBuilder
    );
    
    @Operation(
        summary = "Busca todos os currículos ordenados e paginados.",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "403", ref = "forbidden"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    @SecurityRequirement(name = "bearerKey")
    ResponseEntity<List<ResRegistroCurriculoDTO>> findAll(
        int page, int size
    );
}
