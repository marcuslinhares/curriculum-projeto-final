package dev.marcus.curriculum.controllers;

import org.springframework.http.ResponseEntity;

import dev.marcus.curriculum.models.DTOS.responses.ResAutenticacaoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Autenticação")
public interface AutenticacaoController {
    @Operation(
        summary = "Login com BasicAuth para resgatar token JWT.",
        responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", ref = "unauthorized"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    @SecurityRequirement(name = "basicScheme")
    ResponseEntity<ResAutenticacaoDTO> login();
}
