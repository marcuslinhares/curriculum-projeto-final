package dev.marcus.curriculum.controllers.impls;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.controllers.CandidatoController;
import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCandidatoDTO;
import dev.marcus.curriculum.models.DTOS.responses.SituacaoCountDTO;
import dev.marcus.curriculum.services.CandidatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/candidatos")
public class CandidatoControllerImpl implements CandidatoController{
    private final CandidatoService candidatoService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATO')")
    public ResponseEntity<ResRegistroCandidatoDTO> save(
        @Valid @RequestBody ReqRegistroCandidatoDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        var response = candidatoService.save(dto);
        var uri = uriBuilder.path("/candidatos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response); 
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping(path = "/{candidatoId}", params = {"aprovado"})
    public ResponseEntity<ResRegistroCandidatoDTO> aprovaReprovaCandidato(
        @PathVariable UUID candidatoId, @RequestParam boolean aprovado
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            this.candidatoService.aprovarReprovarCandidato(candidatoId, aprovado)
        ); 
    }

    @Override
    @GetMapping("/sitacoes")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<SituacaoCountDTO>> getSituacaoCounts() {
        return ResponseEntity.status(HttpStatus.OK).body(
            candidatoService.obterQuantidadePorSituacao()
        );
    }
}
