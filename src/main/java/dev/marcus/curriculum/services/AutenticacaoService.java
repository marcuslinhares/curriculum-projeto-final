package dev.marcus.curriculum.services;

import org.springframework.security.core.Authentication;

import dev.marcus.curriculum.models.DTOS.responses.ResAutenticacaoDTO;
import dev.marcus.curriculum.models.entities.UsuarioEntity;

public interface AutenticacaoService {
    ResAutenticacaoDTO authenticate(Authentication authentication);
    UsuarioEntity getLoggedUser();
}
