package dev.marcus.curriculum.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name = "Usuarios")
public interface UsuarioController {
    @Operation(
        summary = "Cadastra novo usuário no sistema.",
        responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "409", ref = "conflict"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    ResponseEntity<ResRegistroUsuarioDTO> save(
        @Valid @RequestBody ReqRegistroUsuarioDTO dto,
        UriComponentsBuilder uriBuilder
    );
}
