package dev.marcus.curriculum.models.DTOS.responses;

import java.util.UUID;

public record ResRegistroUsuarioDTO(
    UUID id,
    String nome,
    String email
) {

}
