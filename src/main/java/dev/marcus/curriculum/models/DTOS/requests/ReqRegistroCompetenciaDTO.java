package dev.marcus.curriculum.models.DTOS.requests;

import dev.marcus.curriculum.models.enums.CompetenciaNivelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReqRegistroCompetenciaDTO(
    @NotBlank(message = "A descrição da competẽncia deve ser informada")
    String descricao,

    @NotNull(message = "O nivel da competẽncia deve ser informada")
    CompetenciaNivelEnum nivel
) {

}
