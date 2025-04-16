package dev.marcus.curriculum.controllers.impls;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import dev.marcus.curriculum.controllers.CurriculoController;
import dev.marcus.curriculum.models.DTOS.requests.ReqRegistroCurriculoDTO;
import dev.marcus.curriculum.models.DTOS.responses.ResRegistroCurriculoDTO;
import dev.marcus.curriculum.services.CurriculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/curriculos")
public class CurriculoControllerImpl implements CurriculoController{
    private final CurriculoService curriculoService;

    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_CANDIDATO')")
    public ResponseEntity<ResRegistroCurriculoDTO> save(
        @Valid @RequestBody ReqRegistroCurriculoDTO dto,
        UriComponentsBuilder uriBuilder
    ) {
        var response = curriculoService.save(dto);
        var uri = uriBuilder.path("/curriculo/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response); 
    }

}
