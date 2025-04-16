package dev.marcus.curriculum.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;

public interface CurriculoService {
    ResRegistroCurriculoDTO save(ReqRegistroCurriculoDTO dto);
    List<ResRegistroCurriculoDTO> findAll(Pageable pageable);
}
