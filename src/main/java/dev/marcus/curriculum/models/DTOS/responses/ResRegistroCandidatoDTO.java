package dev.marcus.curriculum.models.DTOS.responses;

import java.time.LocalDate;
import java.util.UUID;

import dev.marcus.curriculum.models.enums.SituacaoEnum;

public record ResRegistroCandidatoDTO(
    UUID id,
    String cpf,
    LocalDate dataNasc,
    String telefone,
    SituacaoEnum situacao,
    ResRegistroUsuarioDTO usuario
) {

}
