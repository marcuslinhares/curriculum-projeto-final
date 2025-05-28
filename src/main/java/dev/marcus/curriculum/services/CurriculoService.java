package dev.marcus.curriculum.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.EscolaridadeCountDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;

public interface CurriculoService {
    ResRegistroCurriculoDTO save(ReqRegistroCurriculoDTO dto);
    Page<ResRegistroCurriculoDTO> findAll(Pageable pageable);
    ResRegistroCurriculoDTO findByLogedUser();
    ResRegistroCurriculoDTO findById(UUID id);
    List<EscolaridadeCountDTO> obterQuantidadePorEscolaridade();
}
