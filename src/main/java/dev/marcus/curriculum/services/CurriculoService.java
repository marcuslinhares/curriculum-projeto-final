package dev.marcus.curriculum.services;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;

public interface CurriculoService {
    ResRegistroCurriculoDTO save(ReqRegistroCurriculoDTO dto);
}
