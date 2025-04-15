package dev.marcus.curriculum.services;

import java.util.UUID;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;
import dev.marcus.curriculum.models.entities.UsuarioEntity;

public interface UsuarioService {
    ResRegistroUsuarioDTO save(ReqRegistroUsuarioDTO dto);
    UsuarioEntity findById(UUID id);
}
