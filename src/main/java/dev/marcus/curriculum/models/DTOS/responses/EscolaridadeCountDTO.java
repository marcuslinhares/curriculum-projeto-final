package dev.marcus.curriculum.models.DTOS.responses;

import dev.marcus.curriculum.models.enums.EscolaridadeEnum;

public record EscolaridadeCountDTO(
    EscolaridadeEnum escolaridade,
    Long quantidade
) {

}
