package dev.marcus.curriculum.models.DTOS.responses;

import dev.marcus.curriculum.models.enums.SituacaoEnum;

public record SituacaoCountDTO(
    SituacaoEnum situacao,
    Long quantidade
) {

}
