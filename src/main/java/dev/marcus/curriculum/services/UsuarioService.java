package dev.marcus.curriculum.services;

import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroUsuarioDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroUsuarioDTO;

public interface UsuarioService {
    ResRegistroUsuarioDTO save(ReqRegistroUsuarioDTO dto);
}
