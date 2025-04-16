package dev.marcus.curriculum.models.DTOS.requests;

import java.util.List;

import dev.marcus.curriculum.models.enums.EscolaridadeEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReqRegistroCurriculoDTO(

    @NotNull(message = "A escolaridade é obrigatória")
    EscolaridadeEnum escolaridade,

    @NotBlank(message = "A descrição da função é obrigatória")
    String funcao,

    @Valid
    @NotEmpty(message = "Pelo menos uma competência deve ser informada")
    List<ReqRegistroCompetenciaDTO> competencias

) {

}
