package dev.marcus.curriculum.controllers.impls;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.marcus.curriculum.controllers.AutenticacaoController;
import dev.marcus.curriculum.models.DTOS.responses.ResAutenticacaoDTO;
import dev.marcus.curriculum.services.AutenticacaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/autenticacao")
public class AutenticacaoControllerImpl implements AutenticacaoController{
    private final AutenticacaoService autenticacaoService;

    @Override
    @PostMapping
    public ResponseEntity<ResAutenticacaoDTO> login() {
        return ResponseEntity.ok(
            autenticacaoService.authenticate(
                SecurityContextHolder.getContext().getAuthentication()
            )
        );
    }
}
