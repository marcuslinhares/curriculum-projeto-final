package dev.marcus.curriculum.models.DTOS.responses;

import java.util.UUID;

import dev.marcus.curriculum.models.enums.CompetenciaNivelEnum;
import lombok.Builder;

@Builder
public record ResRegistroCompetenciaDTO(
    UUID id,
    String descricao,
    CompetenciaNivelEnum nivel
) {
    
}
