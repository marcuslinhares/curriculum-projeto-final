package dev.marcus.curriculum.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;

public interface CurriculoService {
    ResRegistroCurriculoDTO save(ReqRegistroCurriculoDTO dto);
    Page<ResRegistroCurriculoDTO> findAll(Pageable pageable);
    ResRegistroCurriculoDTO findByLogedUser();
}
