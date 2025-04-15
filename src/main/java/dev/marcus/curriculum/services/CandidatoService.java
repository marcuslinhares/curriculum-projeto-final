package dev.marcus.curriculum.services;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;

public interface CandidatoService {
    ResRegistroCandidatoDTO save(ReqRegistroCandidatoDTO dto);
}
