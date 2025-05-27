package dev.marcus.curriculum.models.mappers;

import java.util.List;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCompetenciaDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;
import dev.marcus.curriculum.models.entities.CurriculoEntity;

public abstract class CurriculoMapper {

    public static CurriculoEntity fromReqRegistroDTOToEntity(
        ReqRegistroCurriculoDTO curriculo
    ){
        return CurriculoEntity.builder()
            .escolaridade(curriculo.escolaridade())
            .funcao(curriculo.funcao())
        .build();
    }

    public static ResRegistroCurriculoDTO fromEntityToResRegistroDTO(
        CurriculoEntity curriculo, ResRegistroCandidatoDTO candidato,
        List<ResRegistroCompetenciaDTO> competencias
    ){
        return new ResRegistroCurriculoDTO(
            curriculo.getId(),
            curriculo.getEscolaridade(),
            curriculo.getFuncao(),
            candidato,
            competencias
        );
    }
}
