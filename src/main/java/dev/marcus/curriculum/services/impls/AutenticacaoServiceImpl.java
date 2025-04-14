package dev.marcus.curriculum.services.impls;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import dev.marcus.curriculum.config.security.AuthenticatedUser;
import dev.marcus.curriculum.config.security.JwtService;
import dev.marcus.curriculum.models.DTOS.responses.ResAutenticacaoDTO;
import dev.marcus.curriculum.models.entities.UsuarioEntity;
import dev.marcus.curriculum.services.AutenticacaoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService{
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public ResAutenticacaoDTO authenticate(Authentication authentication) {
        var authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
        var usuario = authenticatedUser.usuario();

        return new ResAutenticacaoDTO(
            usuario.getId().toString(),
            authenticatedUser.getUsername(),
            jwtService.authenticate(authentication)
        );
    }

    @Override
    public UsuarioEntity getLoggedUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var userDetails = (AuthenticatedUser) userDetailsService.loadUserByUsername(email);
        return userDetails.usuario();
    }
}
