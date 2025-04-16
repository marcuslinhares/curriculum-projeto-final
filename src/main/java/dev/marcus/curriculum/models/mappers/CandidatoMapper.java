package dev.marcus.curriculum.models.mappers;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.entities.CandidatoEntity;

public abstract class CandidatoMapper {

    public static CandidatoEntity fromReqRegistroDTOToEntity(
        ReqRegistroCandidatoDTO dto
    ){
        return CandidatoEntity.builder()
            .cpf(dto.cpf())
            .dataNasc(dto.dataNasc())
            .telefone(dto.telefone())
        .build();
    }

    public static ResRegistroCandidatoDTO fromEntityToResRegistroDTO(
        CandidatoEntity candidato, ResRegistroUsuarioDTO usuario
    ){
        return ResRegistroCandidatoDTO.builder()
            .id(candidato.getId())
            .cpf(candidato.getCpf())
            .dataNasc(candidato.getDataNasc())
            .telefone(candidato.getTelefone())
            .situacao(candidato.getSituacao())
            .usuario(usuario)
        .build();
    }
}
