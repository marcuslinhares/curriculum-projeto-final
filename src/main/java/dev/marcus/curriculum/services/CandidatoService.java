package dev.marcus.curriculum.services;

import java.util.UUID;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.enums.SituacaoEnum;

public interface CandidatoService {
    ResRegistroCandidatoDTO save(ReqRegistroCandidatoDTO dto);
    ResRegistroCandidatoDTO updateSituacao(UUID candidatoId, SituacaoEnum situacao);
    ResRegistroCandidatoDTO aprovarReprovarCandidato(UUID candidatoId, boolean aprovado);
}
