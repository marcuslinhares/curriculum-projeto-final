package dev.marcus.curriculum.models.DTOS.responses;

import java.util.UUID;
import java.util.List;

import dev.marcus.curriculum.models.enums.EscolaridadeEnum;

public record ResRegistroCurriculoDTO(
    UUID id,
    EscolaridadeEnum escolaridade,
    String funcao,
    ResRegistroCandidatoDTO candidato,
    List<ResRegistroCompetenciaDTO> competencias
) {

}
