package dev.marcus.curriculum.models.mappers;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCompetenciaDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCompetenciaDTO;
import dev.marcus.curriculum.models.entities.CompetenciaEntity;

public abstract class CompetenciaMapper {
    public static CompetenciaEntity fromReqRegistroDTOToEntity(
        ReqRegistroCompetenciaDTO competencia
    ){
        return CompetenciaEntity.builder()
            .descricao(competencia.descricao())
            .nivel(competencia.nivel())
        .build();
    }

    public static ResRegistroCompetenciaDTO fromEntityToResRegistroDTO(
        CompetenciaEntity competencia
    ){
        return ResRegistroCompetenciaDTO.builder()
            .id(competencia.getId())
            .descricao(competencia.getDescricao())
            .nivel(competencia.getNivel())
        .build();
    }
}
