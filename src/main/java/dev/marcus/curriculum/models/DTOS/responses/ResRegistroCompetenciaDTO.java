package dev.marcus.curriculum.models.DTOS.responses;

import java.util.UUID;

import dev.marcus.curriculum.models.enums.CompetenciaNivelEnum;

public record ResRegistroCompetenciaDTO(
    UUID id,
    String descricao,
    CompetenciaNivelEnum nivel
) {
    
}
