package dev.marcus.curriculum.models.mappers;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.entities.UsuarioEntity;

public abstract class UsuarioMapper {

    public static UsuarioEntity fromReqRegistroDTOToEntity(
        ReqRegistroUsuarioDTO dto
    ){
        return UsuarioEntity.builder()
            .nome(dto.nome())
            .email(dto.email())
            .senha(dto.senha())
        .build();
    }

    public static ResRegistroUsuarioDTO fromEntityToResRegistroDTO(
        UsuarioEntity usuario
    ){
        return new ResRegistroUsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
        
    }
}
