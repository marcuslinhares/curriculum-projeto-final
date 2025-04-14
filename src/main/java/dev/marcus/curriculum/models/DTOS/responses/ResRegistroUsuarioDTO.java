package dev.marcus.curriculum.models.DTOS.responses;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ResRegistroUsuarioDTO(
    UUID id,
    String nome,
    String email
) {

}
